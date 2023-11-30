package ru.pegasagro.car;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/cars")
public class CarController {

    private final CarService carService;

    @Autowired
    public CarController(CarService carService) {
        this.carService = carService;
    }

    @PostMapping
    public ResponseEntity<Car> createCar(@RequestBody Car car) {
        Car createdCar = carService.createCar(car);
        return new ResponseEntity<>(createdCar, HttpStatus.CREATED);
    }

    @PostMapping("/{carId}/assign/{ownerId}")
    public ResponseEntity<Void> assignOwnerToCar(@PathVariable Long carId, @PathVariable Long ownerId) {
        carService.assignOwnerToCar(carId, ownerId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/{carId}/remove-owner")
    public ResponseEntity<Void> removeOwnerFromCar(@PathVariable Long carId) {
        carService.removeOwnerFromCar(carId);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}



