package com.model;

import java.math.BigDecimal;
import  lombok.Setter;
import  lombok.Getter;

@Getter
@Setter
public class Sportcar extends Vehicle{
    private String maxSpeed;

    public Sportcar(String model, Manufacturer manufacturer, BigDecimal price,String maxSpeed) {
        super(model, manufacturer, price);
        this.maxSpeed = maxSpeed;
    }

    @Override
    public String toString() {
        return "Sportcar{" +
                "maxSpeed=" + maxSpeed +
                ", id='" + id + '\'' +
                ", model='" + model + '\'' +
                ", price=" + price +
                ", manufacturer=" + manufacturer +
                '}';
    }
}
