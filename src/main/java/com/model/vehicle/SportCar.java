package com.model.vehicle;

import java.math.BigDecimal;
import java.util.Comparator;

import lombok.Setter;
import lombok.Getter;

@Getter
@Setter
public class SportCar extends Vehicle {
    private String maxSpeed;

    public SportCar(String model, Manufacturer manufacturer, BigDecimal price, String maxSpeed, int count) {
        super(model, manufacturer, price, count);
        this.maxSpeed = maxSpeed;
    }

    @Override
    public String toString() {
        return "SportCar{" +
                "maxSpeed=" + maxSpeed +
                ", id='" + id + '\'' +
                ", model='" + model + '\'' +
                ", price=" + price +
                ", manufacturer=" + manufacturer +
                '}';
    }

    public static class SportCarComparator implements Comparator<SportCar> {

        @Override
        public int compare(SportCar o1, SportCar o2) {
            return o1.getId().compareTo(o2.getId());
        }
    }
}
