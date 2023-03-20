package com;

import com.model.*;
import com.repository.AutoRepository;
import com.repository.BusRepository;
import com.repository.SportCarRepository;
import com.service.AutoService;
import com.service.BusService;
import com.service.SportCarService;

import java.math.BigDecimal;
import java.util.List;

public class Main {
    private static final AutoService AUTO_SERVICE = new AutoService(new AutoRepository());
    private static final BusService BUS_SERVICE = new BusService(new BusRepository());
    private static final SportCarService SPORT_CAR_SERVICE = new SportCarService(new SportCarRepository());

    public static void main(String[] args) {
        final List<Auto> autos = AUTO_SERVICE.createAutos(5);
        AUTO_SERVICE.saveAutos(autos);
        final List<Bus> buses = BUS_SERVICE.createBuses(5);
        BUS_SERVICE.saveBuses(buses);
        final List<SportCar> sportCars = SPORT_CAR_SERVICE.createSportCars(5);
        SPORT_CAR_SERVICE.saveSportCars(sportCars);

        AUTO_SERVICE.printAll();
        System.out.println("*".repeat(120));
        BUS_SERVICE.printAll();
        System.out.println("*".repeat(120));
        SPORT_CAR_SERVICE.printAll();

        SportCar car = sportCars.get(0);
        car.setMaxSpeed("550");
        car.setManufacturer(Manufacturer.BMW);
        car.setModel("Model-XXX");
        car.setPrice(BigDecimal.valueOf(1200.5552));

        System.out.println("*".repeat(60) + "UPDATE" + "*".repeat(60));
        SPORT_CAR_SERVICE.updateSportCars(car);
        SPORT_CAR_SERVICE.printAll();

        System.out.println("*".repeat(60) + "DELETE" + "*".repeat(60));
        SPORT_CAR_SERVICE.deleteSportCar(car);
        SPORT_CAR_SERVICE.printAll();

    }
}