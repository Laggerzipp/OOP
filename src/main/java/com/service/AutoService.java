package com.service;

import com.model.Auto;
import com.repository.CrudRepository;

import java.math.BigDecimal;
import java.util.List;

public class AutoService extends VehicleService<Auto> {

    public AutoService(CrudRepository<Auto> repository) {
        super(repository);
    }

    @Override
    protected Auto create() {
        return new Auto(
                "Model-" + RANDOM.nextInt(1000),
                getRandomManufacturer(),
                BigDecimal.valueOf(RANDOM.nextDouble(1000.0)),
                "Model-" + RANDOM.nextInt(1000),
                100);
    }

    public void getTotalSumOf(String id) {
        repository.findById(id).ifPresent(auto -> {
            final int count = auto.getCount();
            final BigDecimal price = auto.getPrice();
            final int totalSum = count * price.intValue();
            System.out.printf("Auto %s has total sum %d%n", auto.getModel(), totalSum);
        });
    }

    public Auto getOrCreate(String id) {
        final Auto auto = repository.findById(id).orElse(create());
        repository.save(auto);
        return auto;
    }

    public Auto getOrCreateWithTotalSum(String id) {
        final Auto auto = repository.findById(id).orElseGet(() -> {
            final List<Auto> all = repository.getAll();
            BigDecimal totalSum = BigDecimal.ZERO;
            for (Auto a : all) {
                totalSum = totalSum.add(a.getPrice());
            }
            final Auto createOne = create();
            createOne.setPrice(totalSum);
            return createOne;
        });
        repository.save(auto);
        return auto;
    }

    public void findOneById(String id) {
        if (id == null) {
            repository.findById("");
        } else {
            repository.findById(id);
        }
    }
}
