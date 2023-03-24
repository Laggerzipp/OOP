package com;

import com.model.vehicle.*;
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
        final List<Auto> autos = AUTO_SERVICE.createAndSave(5);
        AUTO_SERVICE.saveAll(autos);
        final List<Bus> buses = BUS_SERVICE.createAndSave(5);
        BUS_SERVICE.saveAll(buses);
        final List<SportCar> sportCars = SPORT_CAR_SERVICE.createAndSave(5);
        SPORT_CAR_SERVICE.saveAll(sportCars);

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
        SPORT_CAR_SERVICE.update(car);
        SPORT_CAR_SERVICE.printAll();

        System.out.println("*".repeat(60) + "DELETE" + "*".repeat(60));
        SPORT_CAR_SERVICE.delete(car);
        SPORT_CAR_SERVICE.printAll();

        AUTO_SERVICE.getTotalSumOf(autos.get(2).getId());

        Garage<Bus> busGarage = new Garage<>();
        busGarage.add(buses.get(0));
        busGarage.addFirst(buses.get(1));
        busGarage.add(1, buses.get(2));
        System.out.println(busGarage.toString());
        busGarage.set(0, buses.get(3));
        System.out.println(busGarage.toString());
    }
}