package com.service;

import com.model.Auto;
import com.model.Bus;
import com.model.Manufacturer;
import com.model.SportCar;
import com.repository.AutoRepository;
import com.repository.BusRepository;
import com.repository.CrudRepository;
import com.repository.SportCarRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.math.BigDecimal;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

class VehicleServiceTest {

    private AutoService targetAutoService;
    private AutoRepository autoRepository;

    private VehicleService<Bus> targetBusService;
    private CrudRepository<Bus> busRepository;

    private VehicleService<SportCar> targetSportCarService;
    private CrudRepository<SportCar> sportCarRepository;

    @BeforeEach
    void setUp() {
        autoRepository = Mockito.mock(AutoRepository.class);
        targetAutoService = new AutoService(autoRepository);

        busRepository = new BusRepository();
        targetBusService = new BusService(busRepository);

        sportCarRepository = new SportCarRepository();
        targetSportCarService = new SportCarService(sportCarRepository);
    }

    private Auto createSimpleAuto() {
        return new Auto("Model", Manufacturer.BMW, BigDecimal.ZERO, "Type", 1);
    }

    @Test
    void createAutos_negativeCount() {
        final List<Auto> actual = targetAutoService.createAndSave(-1);
        Assertions.assertEquals(0, actual.size());
    }

    @Test
    void createAutos_zeroCount() {
        final List<Auto> actual = targetAutoService.createAndSave(0);
        Assertions.assertEquals(0, actual.size());
    }

    @Test
    void createAutos() {
        targetAutoService.createAndSave(5);
        Mockito.verify(autoRepository, Mockito.times(5)).save(Mockito.any());
    }

    @Test
    void printAll() {
        List<Auto> autos = List.of(createSimpleAuto(), createSimpleAuto());
        Mockito.when(autoRepository.getAll()).thenReturn(autos);
        targetAutoService.printAll();
    }

    @Test
    void updateAuto() {
        final List<Auto> autos = targetAutoService.createAndSave(2);
        Mockito.verify(autoRepository, Mockito.times(2)).save(Mockito.any());
        final Auto auto = autos.get(1);
        Assertions.assertEquals(auto.getId(), autos.get(1).getId());
        auto.setManufacturer(Manufacturer.ZAZ);
        targetAutoService.update(auto);
        Assertions.assertEquals(autos.get(1).getManufacturer(), Manufacturer.ZAZ);
    }

    @Test
    void updateAuto_bodyTypEmpty_hasOne() {
        final Auto simpleAuto = createSimpleAuto();
        Mockito.when(autoRepository.findById(simpleAuto.getId())).thenReturn(Optional.of(simpleAuto));

        simpleAuto.setBodyType("");
        targetAutoService.update(simpleAuto);
        Mockito.verify(autoRepository).update(simpleAuto);
    }


    private Bus createBus() {
        return new Bus("Model", Manufacturer.ZAZ, BigDecimal.ZERO, "Line-000", 1);
    }

    @Test
    void createBuses_zeroNegativeCount() {
        final List<Bus> actual = targetBusService.createAndSave(0);
        Assertions.assertEquals(0, actual.size());
        final List<Bus> actualBus = targetBusService.createAndSave(-2);
        Assertions.assertEquals(0, actualBus.size());
    }

    @Test
    void createBuses_positiveCount() {
        final List<Bus> actual = targetBusService.createAndSave(10);
        Assertions.assertEquals(10, actual.size());
    }

    @Test
    void saveBus() {
        final Bus bus = createBus();
        targetBusService.save(bus);
        final List<Bus> actualBuses = busRepository.getAll();
        Assertions.assertEquals(busRepository.getAll().size(), actualBuses.size());
    }

    @Test
    void saveBuses() {
        final Bus bus1 = createBus();
        final Bus bus2 = createBus();
        final List<Bus> actual = new LinkedList<>();
        actual.add(bus1);
        actual.add(bus2);
        targetBusService.saveAll(actual);
        final List<Bus> actualBuses = busRepository.getAll();
        Assertions.assertEquals(actual.size(), actualBuses.size());
    }

    @Test
    void updateBus() {
        final List<Bus> buses = targetBusService.createAndSave(3);
        final Bus bus = buses.get(1);
        bus.setLineName("555");
        targetBusService.update(bus);

        final Optional<Bus> actual = busRepository.findById(bus.getId());
        actual.ifPresent(b -> Assertions.assertEquals("555", b.getLineName()));
    }

    @Test
    void deleteBus_true() {
        final List<Bus> buses = targetBusService.createAndSave(3);
        final Optional<Bus> actual = busRepository.findById(buses.get(2).getId());

        actual.ifPresent(b -> Assertions.assertTrue(targetBusService.delete(b)));

        final List<Bus> actualCars = busRepository.getAll();
        Assertions.assertEquals(2, actualCars.size());
    }

    @Test
    void deleteBus_false() {
        targetBusService.createAndSave(3);
        final Optional<Bus> actual = Optional.of(createBus());

        actual.ifPresent(b -> Assertions.assertFalse(targetBusService.delete(b)));

        final List<Bus> actualCars = busRepository.getAll();
        Assertions.assertEquals(3, actualCars.size());
    }

    private SportCar createSportcar() {
        return new SportCar("Model", Manufacturer.ZAZ, BigDecimal.ZERO, "000", 1);
    }

    @Test
    void createSportCars_zeroCount() {
        final List<SportCar> actual = targetSportCarService.createAndSave(0);
        Assertions.assertEquals(0, actual.size());
    }

    @Test
    void createSportCars_negativeCount() {
        final List<SportCar> actual = targetSportCarService.createAndSave(-2);
        Assertions.assertEquals(0, actual.size());
    }

    @Test
    void createSportCars_positiveCount() {
        final List<SportCar> actual = targetSportCarService.createAndSave(3);
        Assertions.assertEquals(3, actual.size());
    }

    @Test
    void saveSportCar() {
        final SportCar car = createSportcar();
        targetSportCarService.save(car);
        final List<SportCar> actualCars = sportCarRepository.getAll();
        Assertions.assertEquals(sportCarRepository.getAll().size(), actualCars.size());
    }

    @Test
    void saveSportCars() {
        final SportCar tmp1 = createSportcar();
        final SportCar tmp2 = createSportcar();
        final List<SportCar> actual = new LinkedList<>();
        actual.add(tmp1);
        actual.add(tmp2);
        targetSportCarService.saveAll(actual);
        final List<SportCar> actualCars = sportCarRepository.getAll();
        Assertions.assertEquals(actual.size(), actualCars.size());
    }

    @Test
    void updateSportCars() {
        final List<SportCar> cars = targetSportCarService.createAndSave(3);
        final SportCar car = cars.get(1);
        car.setMaxSpeed("555");
        targetSportCarService.update(car);

        final Optional<SportCar> actual = sportCarRepository.findById(car.getId());
        actual.ifPresent(c -> Assertions.assertEquals("555", c.getMaxSpeed()));
    }

    @Test
    void deleteSportCar_true() {
        final List<SportCar> cars = targetSportCarService.createAndSave(3);
        final Optional<SportCar> actual = sportCarRepository.findById(cars.get(2).getId());

        actual.ifPresent(c -> Assertions.assertTrue(targetSportCarService.delete(c)));

        final List<SportCar> actualCars = sportCarRepository.getAll();
        Assertions.assertEquals(2, actualCars.size());
    }

    @Test
    void deleteSportCar_false() {
        targetSportCarService.createAndSave(3);
        final Optional<SportCar> actual = Optional.of(createSportcar());

        actual.ifPresent(c -> Assertions.assertFalse(targetSportCarService.delete(c)));

        final List<SportCar> actualCars = sportCarRepository.getAll();
        Assertions.assertEquals(3, actualCars.size());
    }
}