import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import ru.pegasagro.car.Car;
import ru.pegasagro.car.CarRepository;
import ru.pegasagro.car.CarService;
import ru.pegasagro.owner.Owner;
import ru.pegasagro.owner.OwnerRepository;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

public class CarServiceTest {

    @Mock
    private CarRepository carRepository;

    @Mock
    private OwnerRepository ownerRepository;

    @InjectMocks
    private CarService carService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testCreateCar() {
        Car car = new Car();
        when(carRepository.save(car)).thenReturn(car);

        Car createdCar = carService.createCar(car);

        assertEquals(car, createdCar);
    }

    @Test
    public void testAssignOwnerToCar() {
        Long carId = 1L;
        Long ownerId = 2L;

        Car car = new Car();
        Owner owner = new Owner();

        when(carRepository.findById(carId)).thenReturn(Optional.of(car));
        when(ownerRepository.findById(ownerId)).thenReturn(Optional.of(owner));

        carService.assignOwnerToCar(carId, ownerId);

        assertEquals(owner, car.getOwner());
    }

    @Test
    public void testRemoveOwnerFromCar() {
        Long carId = 1L;

        Car car = new Car();
        car.setOwner(new Owner());

        when(carRepository.findById(carId)).thenReturn(Optional.of(car));

        carService.removeOwnerFromCar(carId);

        assertEquals(null, car.getOwner());
    }
}
