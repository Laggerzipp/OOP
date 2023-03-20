package com.model;

import java.math.BigDecimal;
import  lombok.Setter;
import  lombok.Getter;

@Getter
@Setter
public class SportCar extends Vehicle{
    private String maxSpeed;

    public SportCar(String model, Manufacturer manufacturer, BigDecimal price, String maxSpeed) {
        super(model, manufacturer, price);
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
}
