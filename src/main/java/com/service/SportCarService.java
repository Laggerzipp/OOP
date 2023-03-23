package com.service;

import com.model.vehicle.SportCar;
import com.repository.CrudRepository;

import java.math.BigDecimal;

public class SportCarService extends VehicleService<SportCar> {

    public SportCarService(CrudRepository<SportCar> repository) {
        super(repository);
    }

    @Override
    protected SportCar create() {
        return new SportCar(
                SportCar.class.getSimpleName() + RANDOM.nextInt(1000),
                getRandomManufacturer(),
                BigDecimal.valueOf(RANDOM.nextDouble(1000.0)),
                "" + (RANDOM.nextInt(1000) * 2),
                100
        );
    }


}
