import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import ru.pegasagro.car.Car;
import ru.pegasagro.car.CarController;
import ru.pegasagro.car.CarService;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CarControllerTest {

    @InjectMocks
    private CarController carController;

    @Mock
    private CarService carService;

    @Test
    public void testCreateCar() {
        Car car = new Car();
        car.setIdCar(1L);
        car.setAssemblyDate(LocalDate.now());
        car.setUniqueNumber("AB123C63");

        when(carService.createCar(any(Car.class))).thenReturn(car);

        ResponseEntity<Car> responseEntity = carController.createCar(new Car());

        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
        assertEquals(1L, responseEntity.getBody().getIdCar());
    }

    @Test
    public void testAssignOwnerToCar() {
        Long carId = 1L;
        Long ownerId = 2L;

        ResponseEntity<Void> responseEntity = carController.assignOwnerToCar(carId, ownerId);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        verify(carService).assignOwnerToCar(eq(carId), eq(ownerId));
    }

    @Test
    public void testRemoveOwnerFromCar() {
        Long carId = 1L;

        ResponseEntity<Void> responseEntity = carController.removeOwnerFromCar(carId);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        verify(carService).removeOwnerFromCar(eq(carId));
    }
}
