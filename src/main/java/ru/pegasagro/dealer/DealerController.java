package ru.pegasagro.dealer;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.pegasagro.car.CarDTO;
import ru.pegasagro.owner.Owner;
import ru.pegasagro.owner.OwnerDTO;

import java.util.List;

@RestController
@RequestMapping("/dealers")
@Slf4j
public class DealerController {

    private final DealerService dealerService;

    @Autowired
    public DealerController(DealerService dealerService) {
        this.dealerService = dealerService;
    }

    @GetMapping("/test")
    public String test() {
        return ("Test connection to dealers");
    }

    @PostMapping
    public ResponseEntity<Dealer> createDealer(@RequestBody Dealer dealer) {
        Dealer createdDealer = dealerService.createDealer(dealer);
        return new ResponseEntity<>(createdDealer, HttpStatus.CREATED);
    }

    @PostMapping("/{dealerId}/assign/{ownerId}")
    public ResponseEntity<Void> assignDealerToOwner(@PathVariable Long dealerId, @PathVariable Long ownerId) {
        dealerService.assignDealerToOwner(dealerId, ownerId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/{dealerId}/owners")
    public ResponseEntity<List<OwnerDTO>> getOwnersByDealerId(@PathVariable Long dealerId) {
        List<OwnerDTO> owners = dealerService.getOwnersByDealerId(dealerId);
        return new ResponseEntity<>(owners, HttpStatus.OK);
    }

    @GetMapping("/{dealerId}/cars")
    public ResponseEntity<List<CarDTO>> getCarsByDealerId(@PathVariable Long dealerId) {
        List<CarDTO> cars = dealerService.getCarsByDealerId(dealerId);
        return new ResponseEntity<>(cars, HttpStatus.OK);
    }

}