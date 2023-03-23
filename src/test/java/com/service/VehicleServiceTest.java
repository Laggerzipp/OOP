package com.service;

import com.model.*;
import com.repository.AutoRepository;
import com.repository.BusRepository;
import com.repository.SportCarRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatcher;
import org.mockito.Mockito;

import java.math.BigDecimal;
import java.util.List;

import static org.mockito.ArgumentMatchers.argThat;

class VehicleServiceTest {

    private VehicleService<Auto> targetAutoService;
    protected AutoRepository autoRepository;

    private VehicleService<Bus> targetBusService;
    protected BusRepository busRepository;

    private VehicleService<SportCar> targetSportCarService;
    protected SportCarRepository sportCarRepository;


    @BeforeEach
    void setUp() {
        autoRepository = Mockito.mock(AutoRepository.class);
        targetAutoService = new VehicleService<>(autoRepository) {
            @Override
            protected Auto create() {
                return new Auto("Model", Manufacturer.BMW, BigDecimal.ZERO, "Type", 1);
            }
        };

        sportCarRepository = Mockito.mock(SportCarRepository.class);
        targetSportCarService = new VehicleService<>(sportCarRepository) {
            @Override
            protected SportCar create() {
                return new SportCar("Model", Manufacturer.ZAZ, BigDecimal.ZERO, "000", 1);
            }
        };

        busRepository = Mockito.mock(BusRepository.class);
        targetBusService = new VehicleService<>(busRepository) {
            @Override
            protected Bus create() {
                return new Bus("Model", Manufacturer.BMW, BigDecimal.ZERO, "Line", 100);
            }
        };
    }

    private Auto createSimpleAuto() {
        return new Auto("Model", Manufacturer.BMW, BigDecimal.ZERO, "Type", 1);
    }

    private Bus createBus() {
        return new Bus("Model", Manufacturer.BMW, BigDecimal.ZERO, "Type", 1);
    }

    private SportCar createSportcar() {
        return new SportCar("Model", Manufacturer.ZAZ, BigDecimal.ZERO, "000", 1);
    }

    @Test
    void createAutos_negativeCount() {
        //configuration

        //call test method
        final List<Auto> actual = targetAutoService.createAndSave(-1);

        //checks
        Assertions.assertEquals(0, actual.size());
    }

    @Test
    void createAutos_zeroCount() {
        //configuration

        //call test method
        final List<Auto> actual = targetAutoService.createAndSave(0);

        //checks
        Assertions.assertEquals(0, actual.size());
    }

    @Test
    void createAutos() {
        //configuration

        //call test method
        final List<Auto> actual = targetAutoService.createAndSave(5);

        //checks
        Assertions.assertEquals(5, actual.size());
        Mockito.verify(autoRepository, Mockito.times(5)).save(Mockito.any());
    }

    @Test
    void printAll() {
        //configuration
        List<Auto> autos = List.of(createSimpleAuto(), createSimpleAuto());

        //call test method
        targetAutoService.printAll();

        //checks
        Mockito.when(autoRepository.getAll()).thenReturn(autos);
    }

    @Test
    void updateAuto() {
        //configuration
        final Auto actual = createSimpleAuto();
        actual.setManufacturer(Manufacturer.ZAZ);
        Mockito.when(autoRepository.findById(Mockito.anyString())).thenThrow(IllegalStateException.class);

        //call test method
        targetAutoService.update(actual);

        //checks
        Mockito.verify(autoRepository).update(argThat(expected -> actual.getId().equals(expected.getId()) &&
                actual.getManufacturer().equals(expected.getManufacturer())));
    }

    @Test
    void createBuses_zeroCount() {
        //configuration

        //call test method
        final List<Bus> actual = targetBusService.createAndSave(0);

        //checks
        Assertions.assertEquals(0, actual.size());
    }

    @Test
    void createBuses_negativeCount() {
        //configuration

        //call test method
        final List<Bus> actual = targetBusService.createAndSave(-2);

        //checks
        Assertions.assertEquals(0, actual.size());
    }

    @Test
    void createBuses_positiveCount() {

        //configuration

        //call test method
        final List<Bus> actual = targetBusService.createAndSave(10);

        //checks
        Assertions.assertEquals(10, actual.size());
        Mockito.verify(busRepository, Mockito.times(10)).save(Mockito.any(Bus.class));
    }

    @Test
    void saveBus() {

        //configuration
        final Bus bus = createBus();

        //call test method
        targetBusService.save(bus);

        //checks
        Mockito.verify(busRepository, Mockito.atLeastOnce()).save(bus);
    }

    @Test
    void saveBuses() {
        //configuration
        final List<Bus> buses = List.of(
                createBus(),
                createBus(),
                createBus()
        );

        //call test method
        targetBusService.saveAll(buses);

        //checks
        Mockito.verify(busRepository).saveAll(buses);
    }

    @Test
    void updateBus() {
        //configuration
        final Bus actual = createBus();
        actual.setLineName("555");
        Mockito.when(busRepository.findById(Mockito.anyString())).thenThrow(IllegalStateException.class);

        //call test method
        targetBusService.update(actual);

        //checks
        Mockito.verify(busRepository).update(argThat(new ArgumentMatcher<>() {
            @Override
            public boolean matches(Bus expected) {
                return actual.getId().equals(expected.getId()) &&
                        actual.getLineName().equals(expected.getLineName());
            }
        }));
    }

    @Test
    void createSportCars_zeroCount() {
        //configuration

        //call test method
        final List<SportCar> actual = targetSportCarService.createAndSave(0);

        //checks
        Assertions.assertEquals(0, actual.size());
    }

    @Test
    void createSportCars_negativeCount() {
        //configuration

        //call test method
        final List<SportCar> actual = targetSportCarService.createAndSave(-2);

        //checks
        Assertions.assertEquals(0, actual.size());
    }

    @Test
    void createSportCars_positiveCount() {
        //configuration

        //call test method
        final List<SportCar> actual = targetSportCarService.createAndSave(10);

        //checks
        Assertions.assertEquals(10, actual.size());
        Mockito.verify(sportCarRepository, Mockito.times(10)).save(Mockito.any(SportCar.class));
    }

    @Test
    void saveSportCar() {
        //configuration
        final SportCar car = createSportcar();

        //call test method
        targetSportCarService.save(car);

        //checks
        Mockito.verify(sportCarRepository, Mockito.atLeastOnce()).save(car);
    }

    @Test
    void saveSportCars() {
        //configuration
        final List<SportCar> cars = List.of(
                createSportcar(),
                createSportcar(),
                createSportcar()
        );

        //call test method
        targetSportCarService.saveAll(cars);

        //checks
        Mockito.verify(sportCarRepository).saveAll(cars);
    }

    @Test
    void updateSportCars() {
        //configuration
        final SportCar actual = createSportcar();
        actual.setModel("555");
        Mockito.when(sportCarRepository.findById(Mockito.anyString())).thenThrow(IllegalStateException.class);

        //call test method
        targetSportCarService.update(actual);

        //checks
        Mockito.verify(sportCarRepository).update(argThat(new ArgumentMatcher<>() {
            @Override
            public boolean matches(SportCar expected) {
                return actual.getId().equals(expected.getId()) &&
                        actual.getModel().equals(expected.getModel());
            }
        }));
    }
}