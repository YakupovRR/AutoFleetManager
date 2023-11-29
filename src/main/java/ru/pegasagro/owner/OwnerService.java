package ru.pegasagro.owner;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.pegasagro.car.Car;
import ru.pegasagro.car.CarDTO;
import ru.pegasagro.car.CarRepository;
import ru.pegasagro.dealer.Dealer;
import ru.pegasagro.dealer.DealerDTO;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class OwnerService {

    private final OwnerRepository ownerRepository;
    private final CarRepository carRepository;

    @Autowired
    public OwnerService(OwnerRepository ownerRepository, CarRepository carRepository) {
        this.ownerRepository = ownerRepository;
        this.carRepository = carRepository;
    }

    public Owner createOwner(Owner owner) {
        return ownerRepository.save(owner);
    }

    public void addCarToOwner(Long ownerId, Long carId) {
        Owner owner = ownerRepository.findById(ownerId)
                .orElseThrow(() -> new EntityNotFoundException("Owner with id " + ownerId + " not found"));

        Car car = carRepository.findById(carId)
                .orElseThrow(() -> new EntityNotFoundException("Car with id " + carId + " not found"));

        owner.getOwnedCars().add(car);
        car.setOwner(owner);

        ownerRepository.save(owner);
        carRepository.save(car);
    }

    public void removeDealerFromOwner(Long ownerId) {
        Owner owner = ownerRepository.findById(ownerId)
                .orElseThrow(() -> new EntityNotFoundException("Owner not found with id: " + ownerId));

        owner.setDealer(null);
        ownerRepository.save(owner);
    }

    public List<CarDTO> getCarsByOwnerId(Long ownerId) {
        Owner owner = ownerRepository.findById(ownerId)
                .orElseThrow(() -> new EntityNotFoundException("Owner not found with id: " + ownerId));

        List<Car> ownedCars = owner.getOwnedCars();
        return ownedCars.stream()
                .map(this::mapToCarDTO)
                .collect(Collectors.toList());
    }

    private List<CarDTO> mapToCarDTOList(List<Car> cars) {
        return cars.stream()
                .map(this::mapToCarDTO)
                .collect(Collectors.toList());
    }

    private CarDTO mapToCarDTO(Car car) {
        if (car != null) {
            return CarDTO.builder()
                    .idCar(car.getIdCar())
                    .assemblyDate(car.getAssemblyDate())
                    .uniqueNumber(car.getUniqueNumber())
                    .owner(mapToOwnerDTO(car.getOwner()))
                    .build();
        } else {
            return null;
        }
    }

    public OwnerDTO mapToOwnerDTO(Owner owner) {
        if (owner != null) {
            return OwnerDTO.builder()
                    .idOwner(owner.getIdOwner())
                    .fullNameOwner(owner.getFullNameOwner())
                    .phoneNumberOwner(owner.getPhoneNumberOwner())
                    .emailOwner(owner.getEmailOwner())
                    .dealer(mapToDealerDTO(owner.getDealer()))
                    .ownedCars(mapToCarDTOList(owner.getOwnedCars()))
                    .build();
        } else {
            return null;
        }
    }

    private DealerDTO mapToDealerDTO(Dealer dealer) {
        if (dealer != null) {
            return DealerDTO.builder()
                    .idDealer(dealer.getIdDealer())
                    .nameDealer(dealer.getNameDealer())
                    .emailDealer(dealer.getEmailDealer())
                    .representativeName(dealer.getRepresentativeName())
                    .owners(mapToOwnerDTOList(dealer.getOwners()))
                    .build();
        } else {
            return null;
        }
    }

    private List<OwnerDTO> mapToOwnerDTOList(List<Owner> owners) {
        return owners.stream()
                .map(this::mapToOwnerDTO)
                .collect(Collectors.toList());
    }
}
