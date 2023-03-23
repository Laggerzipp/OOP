package com.model.vehicle;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Comparator;

@Getter
@Setter
public class Bus extends Vehicle {
    private String lineName;

    public Bus(String model, Manufacturer manufacturer, BigDecimal price, String lineName, int count) {
        super(model, manufacturer, price, count);
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

   public static class BusComparator implements Comparator<Bus> {

       @Override
       public int compare(Bus o1, Bus o2) {
           return o1.getId().compareTo(o2.getId());
       }
   }
}
