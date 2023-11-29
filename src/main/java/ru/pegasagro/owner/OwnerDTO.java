package ru.pegasagro.owner;

import lombok.Builder;
import lombok.Data;
import ru.pegasagro.car.CarDTO;
import ru.pegasagro.dealer.DealerDTO;

import java.util.List;
import java.util.stream.Collectors;

@Data
@Builder
public class OwnerDTO {
    private Long idOwner;
    private String fullNameOwner;
    private String phoneNumberOwner;
    private String emailOwner;
    private DealerDTO dealer;
    private List<CarDTO> ownedCars;
    public static OwnerDTO fromEntity(Owner owner) {
        return OwnerDTO.builder()
                .idOwner(owner.getIdOwner())
                .fullNameOwner(owner.getFullNameOwner())
                .phoneNumberOwner(owner.getPhoneNumberOwner())
                .emailOwner(owner.getEmailOwner())
                .dealer(owner.getDealer() != null ? DealerDTO.fromEntity(owner.getDealer()) : null)
                .ownedCars(owner.getOwnedCars().stream().map(CarDTO::fromEntity).collect(Collectors.toList()))
                .build();
    }
}


