package com.service;

import com.model.Bus;
import com.repository.CrudRepository;

import java.math.BigDecimal;

public class BusService extends VehicleService<Bus> {

    public BusService(CrudRepository<Bus> repository) {
        super(repository);
    }

    @Override
    protected Bus create() {
        return new Bus(
                "Model-" + RANDOM.nextInt(1000),
                getRandomManufacturer(),
                BigDecimal.valueOf(RANDOM.nextDouble(1000.0)),
                "Line-" + RANDOM.nextInt(1000),
                100
        );
    }
}
