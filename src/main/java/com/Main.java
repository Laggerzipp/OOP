package com;

import com.model.*;
import com.repository.AutoRepository;
import com.repository.BusRepository;
import com.repository.SportcarRepository;
import com.service.AutoService;
import com.service.BusService;
import com.service.SportcarService;

import java.math.BigDecimal;
import java.util.List;

public class Main {
    private static final AutoService AUTO_SERVICE = new AutoService(new AutoRepository());
    private static final BusService BUS_SERVICE = new BusService(new BusRepository());
    private static final SportcarService SPORTCAR_SERVICE = new SportcarService(new SportcarRepository());

    public static void main(String[] args) {
        final List<Auto> autos = AUTO_SERVICE.createAutos(5);
        AUTO_SERVICE.saveAutos(autos);
        final List<Bus> buses = BUS_SERVICE.createBuses(5);
        BUS_SERVICE.saveBuses(buses);
        final List<Sportcar> sportcars = SPORTCAR_SERVICE.createSportcars(5);
        SPORTCAR_SERVICE.saveSportcars(sportcars);

        AUTO_SERVICE.printAll();
        System.out.println("*".repeat(120));
        BUS_SERVICE.printAll();
        System.out.println("*".repeat(120));
        SPORTCAR_SERVICE.printAll();

        Sportcar car = sportcars.get(0);
        car.setMaxSpeed("550");
        car.setManufacturer(Manufacturer.BMW);
        car.setModel("Model-XXX");
        car.setPrice(BigDecimal.valueOf(1200.5552));

        System.out.println("*".repeat(60) + "UPDATE" + "*".repeat(60));
        SPORTCAR_SERVICE.updateSportcars(car);
        SPORTCAR_SERVICE.printAll();

        System.out.println("*".repeat(60) + "DELETE" + "*".repeat(60));
        SPORTCAR_SERVICE.deleteSportcar(car);
        SPORTCAR_SERVICE.printAll();

    }
}