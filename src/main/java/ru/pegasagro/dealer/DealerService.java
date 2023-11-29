package ru.pegasagro.dealer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.pegasagro.car.CarDTO;
import ru.pegasagro.owner.Owner;
import ru.pegasagro.owner.OwnerDTO;
import ru.pegasagro.owner.OwnerRepository;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;

import ru.pegasagro.owner.OwnerService;


@Service
public class DealerService {

    private final DealerRepository dealerRepository;
    private final OwnerRepository ownerRepository;
    private final OwnerService ownerService;

    @Autowired
    public DealerService(DealerRepository dealerRepository, OwnerRepository ownerRepository, OwnerService ownerService) {
        this.dealerRepository = dealerRepository;
        this.ownerRepository = ownerRepository;
        this.ownerService = ownerService;
    }

    public Dealer createDealer(Dealer dealer) {
        return dealerRepository.save(dealer);
    }

    public void assignDealerToOwner(Long dealerId, Long ownerId) {
        Dealer dealer = dealerRepository.findById(dealerId)
                .orElseThrow(() -> new EntityNotFoundException("Dealer not found with id: " + dealerId));

        Owner owner = ownerRepository.findById(ownerId)
                .orElseThrow(() -> new EntityNotFoundException("Owner not found with id: " + ownerId));

        owner.setDealer(dealer);
        ownerRepository.save(owner);
        OwnerDTO ownerDTO = ownerService.mapToOwnerDTO(owner);
    }

    public List<OwnerDTO> getOwnersByDealerId(Long dealerId) {
        Dealer dealer = dealerRepository.findById(dealerId)
                .orElseThrow(() -> new EntityNotFoundException("Dealer not found with id: " + dealerId));

        List<Owner> owners = dealer.getOwners();
        List<OwnerDTO> ownerDTOs = new ArrayList<>();

        for (Owner owner : owners) {
            OwnerDTO ownerDTO = OwnerDTO.builder()
                    .idOwner(owner.getIdOwner())
                    .fullNameOwner(owner.getFullNameOwner())
                    .phoneNumberOwner(owner.getPhoneNumberOwner())
                    .emailOwner(owner.getEmailOwner())
                    .build();

            ownerDTOs.add(ownerDTO);
        }

        return ownerDTOs;
    }

    public List<CarDTO> getCarsByDealerId(Long dealerId) {
        List<OwnerDTO> owners = getOwnersByDealerId(dealerId);
        List<CarDTO> cars = new ArrayList<>();

        for (OwnerDTO ownerDTO : owners) {
            List<CarDTO> ownedCars = ownerService.getCarsByOwnerId(ownerDTO.getIdOwner());
            cars.addAll(ownedCars);
        }

        return cars;
    }
}