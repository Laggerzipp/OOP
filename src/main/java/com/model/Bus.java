package com.model;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class Bus extends Vehicle {
    private String lineName;

    public Bus(String model, Manufacturer manufacturer, BigDecimal price, String lineName) {
        super(model, manufacturer, price);
        this.lineName = lineName;
    }

    @Override
    public String toString() {
        return "Bus{" +
                "lineName='" + lineName + '\'' +
                ", id='" + id + '\'' +
                ", model='" + model + '\'' +
                ", price=" + price +
                ", manufacturer=" + manufacturer +
                '}';
    }
}
