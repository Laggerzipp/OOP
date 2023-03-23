package com.model.vehicle;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Comparator;

@Getter
@Setter
public class Auto extends Vehicle {
    private String bodyType;

    public Auto(String model, Manufacturer manufacturer, BigDecimal price, String bodyType, int count) {
        super(model, manufacturer, price, count);
        this.bodyType = bodyType;
    }

    @Override
    public String toString() {
        return "Auto{" + "bodyType='" + bodyType + '\'' + ", id='" + id + '\'' + ", model='" + model + '\'' + ", price=" + price + ", manufacturer=" + manufacturer + '}';
    }

    public static class AutoComparator implements Comparator<Auto> {

        @Override
        public int compare(Auto o1, Auto o2) {
            return o1.getId().compareTo(o2.getId());
        }
    }
}
