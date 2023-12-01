import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import ru.pegasagro.car.CarDTO;
import ru.pegasagro.owner.Owner;
import ru.pegasagro.owner.OwnerController;
import ru.pegasagro.owner.OwnerService;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class OwnerControllerTest {

    @Mock
    private OwnerService ownerService;

    @InjectMocks
    private OwnerController ownerController;

    @Test
    public void testCreateOwner() {
        Owner owner = new Owner();
        when(ownerService.createOwner(any(Owner.class))).thenReturn(owner);

        ResponseEntity<Owner> responseEntity = ownerController.createOwner(owner);

        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
        assertEquals(owner, responseEntity.getBody());
    }

    @Test
    public void testAddCarToOwner() {
        Long ownerId = 1L;
        Long carId = 2L;
        doNothing().when(ownerService).addCarToOwner(anyLong(), anyLong());

        ResponseEntity<Void> responseEntity = ownerController.addCarToOwner(ownerId, carId);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        verify(ownerService, times(1)).addCarToOwner(ownerId, carId);
    }

    @Test
    public void testRemoveOwnerFromCar() {
        Long ownerId = 1L;
        doNothing().when(ownerService).removeDealerFromOwner(anyLong());

        ResponseEntity<Void> responseEntity = ownerController.removeOwnerFromCar(ownerId);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        verify(ownerService, times(1)).removeDealerFromOwner(ownerId);
    }

    @Test
    public void testGetCarsByOwnerId() {
        Long ownerId = 1L;
        when(ownerService.getCarsByOwnerId(anyLong())).thenReturn(Collections.singletonList(mock(CarDTO.class)));

        ResponseEntity<List<CarDTO>> responseEntity = ownerController.getCarsByOwnerId(ownerId);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(1, responseEntity.getBody().size());
    }
}
