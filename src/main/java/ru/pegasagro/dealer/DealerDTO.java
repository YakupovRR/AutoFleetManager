package ru.pegasagro.dealer;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import ru.pegasagro.owner.OwnerDTO;

import java.util.List;
import java.util.stream.Collectors;

//@Data
@Getter
@Setter
@Builder
public class DealerDTO {
    private Long idDealer;
    private String nameDealer;
    private String emailDealer;
    private String representativeName;
    private List<OwnerDTO> owners;

    public static DealerDTO fromEntity(Dealer dealer) {
        return DealerDTO.builder()
                .idDealer(dealer.getIdDealer())
                .nameDealer(dealer.getNameDealer())
                .emailDealer(dealer.getEmailDealer())
                .representativeName(dealer.getRepresentativeName())
                .owners(dealer.getOwners() != null ? dealer.getOwners().stream()
                        .map(OwnerDTO::fromEntity).collect(Collectors.toList()) : null)
                .build();
    }
}
