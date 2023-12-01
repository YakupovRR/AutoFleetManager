import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import ru.pegasagro.car.CarDTO;
import ru.pegasagro.dealer.Dealer;
import ru.pegasagro.dealer.DealerController;
import ru.pegasagro.dealer.DealerService;
import ru.pegasagro.owner.OwnerDTO;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class DealerControllerTest {

    @InjectMocks
    private DealerController dealerController;

    @Mock
    private DealerService dealerService;

    @Test
    public void testCreateDealer() {
        Dealer dealer = new Dealer();
        dealer.setIdDealer(1L);
        dealer.setNameDealer("Test Dealer");

        when(dealerService.createDealer(any(Dealer.class))).thenReturn(dealer);

        ResponseEntity<Dealer> responseEntity = dealerController.createDealer(new Dealer());

        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
        assertEquals(1L, responseEntity.getBody().getIdDealer());
    }

    @Test
    public void testAssignDealerToOwner() {
        Long dealerId = 1L;
        Long ownerId = 2L;

        ResponseEntity<Void> responseEntity = dealerController.assignDealerToOwner(dealerId, ownerId);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        verify(dealerService).assignDealerToOwner(eq(dealerId), eq(ownerId));
    }

    @Test
    public void testGetOwnersByDealerId() {
        Long dealerId = 1L;
        List<OwnerDTO> ownerDTOs = Collections.singletonList(mock(OwnerDTO.class));

        when(dealerService.getOwnersByDealerId(anyLong())).thenReturn(ownerDTOs);

        ResponseEntity<List<OwnerDTO>> responseEntity = dealerController.getOwnersByDealerId(dealerId);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(ownerDTOs, responseEntity.getBody());
    }

    @Test
    public void testGetCarsByDealerId() {
        Long dealerId = 1L;
        List<CarDTO> carDTOs = Collections.singletonList(mock(CarDTO.class));

        when(dealerService.getCarsByDealerId(anyLong())).thenReturn(carDTOs);

        ResponseEntity<List<CarDTO>> responseEntity = dealerController.getCarsByDealerId(dealerId);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(carDTOs, responseEntity.getBody());
    }
}
