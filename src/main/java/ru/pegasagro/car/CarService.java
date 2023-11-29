package ru.pegasagro.car;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.pegasagro.owner.Owner;
import ru.pegasagro.owner.OwnerRepository;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CarService {

    private final CarRepository carRepository;
    private final OwnerRepository ownerRepository;

    @Autowired
    public CarService(CarRepository carRepository, OwnerRepository ownerRepository) {
        this.carRepository = carRepository;
        this.ownerRepository = ownerRepository;
    }

    public Car createCar(Car car) {
        return carRepository.save(car);
    }

    public void assignOwnerToCar(Long carId, Long ownerId) {
        Car car = carRepository.findById(carId)
                .orElseThrow(() -> new EntityNotFoundException("Car not found with id: " + carId));

        Owner owner = ownerRepository.findById(ownerId)
                .orElseThrow(() -> new EntityNotFoundException("Owner not found with id: " + ownerId));

        car.setOwner(owner);
        carRepository.save(car);
    }

    public void removeOwnerFromCar(Long carId) {
        Car car = carRepository.findById(carId)
                .orElseThrow(() -> new EntityNotFoundException("Car not found with id: " + carId));

        car.setOwner(null);
        carRepository.save(car);
    }

    private CarDTO mapToCarDTO(Car car) {
        return CarDTO.builder()
                .idCar(car.getIdCar())
                .assemblyDate(car.getAssemblyDate())
                .uniqueNumber(car.getUniqueNumber())
                .build();
    }

}