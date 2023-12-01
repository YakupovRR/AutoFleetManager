import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.pegasagro.car.Car;
import ru.pegasagro.car.CarDTO;
import ru.pegasagro.car.CarRepository;
import ru.pegasagro.owner.Owner;
import ru.pegasagro.owner.OwnerRepository;
import ru.pegasagro.owner.OwnerService;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class OwnerServiceTest {

    @Mock
    private OwnerRepository ownerRepository;

    @Mock
    private CarRepository carRepository;

    @InjectMocks
    private OwnerService ownerService;

    @Test
    public void testCreateOwner() {
        Owner owner = new Owner();
        when(ownerRepository.save(any(Owner.class))).thenReturn(owner);

        Owner createdOwner = ownerService.createOwner(owner);

        assertEquals(owner, createdOwner);
        verify(ownerRepository, times(1)).save(owner);
    }

    @Test
    public void testRemoveDealerFromOwner() {
        Long ownerId = 1L;
        Owner owner = new Owner();
        when(ownerRepository.findById(anyLong())).thenReturn(Optional.of(owner));

        ownerService.removeDealerFromOwner(ownerId);

        assertNull(owner.getDealer());
        verify(ownerRepository, times(1)).save(owner);
    }


}
