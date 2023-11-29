package ru.pegasagro.owner;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.pegasagro.car.CarDTO;

import java.util.List;


@RestController
@RequestMapping("/owners")
@Slf4j
public class OwnerController {

    private final OwnerService ownerService;

    @Autowired
    public OwnerController(OwnerService ownerService) {
        this.ownerService = ownerService;
    }

    @PostMapping
    public ResponseEntity<Owner> createOwner(@RequestBody Owner owner) {
        Owner createdOwner = ownerService.createOwner(owner);
        return new ResponseEntity<>(createdOwner, HttpStatus.CREATED);
    }

    @PostMapping("/{ownerId}/add-car")
    public ResponseEntity<Void> addCarToOwner(@PathVariable Long ownerId, @RequestParam Long carId) {
        ownerService.addCarToOwner(ownerId, carId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/{ownerId}/remove-dealer")
    public ResponseEntity<Void> removeOwnerFromCar(@PathVariable Long ownerId) {
        ownerService.removeDealerFromOwner(ownerId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/{ownerId}/cars")
    public ResponseEntity<List<CarDTO>> getCarsByOwnerId(@PathVariable Long ownerId) {
        List<CarDTO> ownedCars = ownerService.getCarsByOwnerId(ownerId);
        return new ResponseEntity<>(ownedCars, HttpStatus.OK);
    }
}



