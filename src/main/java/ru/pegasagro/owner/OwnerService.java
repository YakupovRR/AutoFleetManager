package ru.pegasagro.owner;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.pegasagro.car.Car;
import ru.pegasagro.car.CarDTO;
import ru.pegasagro.car.CarRepository;


import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

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
        ownerRepository.save(owner);
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

        if (owner == null) {
            return Collections.emptyList();
        }

        List<Car> ownedCars = owner.getOwnedCars();
        List<CarDTO> carDTOs = new ArrayList<>();
        for (Car car : ownedCars) {
            CarDTO carDTO = CarDTO.builder()
                    .idCar(car.getIdCar())
                    .assemblyDate(car.getAssemblyDate())
                    .uniqueNumber(car.getUniqueNumber())
                    .build();

            carDTOs.add(carDTO);
        }

        return carDTOs;
    }

}
