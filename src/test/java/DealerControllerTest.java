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
        // Arrange
        Dealer dealer = new Dealer();
        dealer.setIdDealer(1L);
        dealer.setNameDealer("Test Dealer");

        when(dealerService.createDealer(any(Dealer.class))).thenReturn(dealer);

        // Act
        ResponseEntity<Dealer> responseEntity = dealerController.createDealer(new Dealer());

        // Assert
        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
        assertEquals(1L, responseEntity.getBody().getIdDealer());
    }

    @Test
    public void testAssignDealerToOwner() {
        // Arrange
        Long dealerId = 1L;
        Long ownerId = 2L;

        // Act
        ResponseEntity<Void> responseEntity = dealerController.assignDealerToOwner(dealerId, ownerId);

        // Assert
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        verify(dealerService).assignDealerToOwner(eq(dealerId), eq(ownerId));
    }

    @Test
    public void testGetOwnersByDealerId() {
        // Arrange
        Long dealerId = 1L;
        List<OwnerDTO> ownerDTOs = Collections.singletonList(mock(OwnerDTO.class));

        when(dealerService.getOwnersByDealerId(anyLong())).thenReturn(ownerDTOs);

        // Act
        ResponseEntity<List<OwnerDTO>> responseEntity = dealerController.getOwnersByDealerId(dealerId);

        // Assert
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(ownerDTOs, responseEntity.getBody());
    }

    @Test
    public void testGetCarsByDealerId() {
        // Arrange
        Long dealerId = 1L;
        List<CarDTO> carDTOs = Collections.singletonList(mock(CarDTO.class));

        when(dealerService.getCarsByDealerId(anyLong())).thenReturn(carDTOs);

        // Act
        ResponseEntity<List<CarDTO>> responseEntity = dealerController.getCarsByDealerId(dealerId);

        // Assert
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(carDTOs, responseEntity.getBody());
    }
}
