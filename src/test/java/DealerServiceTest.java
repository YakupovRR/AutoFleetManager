import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import ru.pegasagro.dealer.Dealer;
import ru.pegasagro.dealer.DealerRepository;
import ru.pegasagro.dealer.DealerService;
import ru.pegasagro.owner.Owner;
import ru.pegasagro.owner.OwnerRepository;


import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

public class DealerServiceTest {

    @Mock
    private DealerRepository dealerRepository;

    @Mock
    private OwnerRepository ownerRepository;

    @InjectMocks
    private DealerService dealerService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testCreateDealer() {
        Dealer dealer = new Dealer();
        when(dealerRepository.save(dealer)).thenReturn(dealer);

        Dealer createdDealer = dealerService.createDealer(dealer);

        assertEquals(dealer, createdDealer);
    }

    @Test
    public void testAssignDealerToOwner() {
        Long dealerId = 1L;
        Long ownerId = 2L;

        Dealer dealer = new Dealer();
        Owner owner = new Owner();

        when(dealerRepository.findById(dealerId)).thenReturn(Optional.of(dealer));
        when(ownerRepository.findById(ownerId)).thenReturn(Optional.of(owner));

        dealerService.assignDealerToOwner(dealerId, ownerId);

        assertEquals(dealer, owner.getDealer());
    }

}
