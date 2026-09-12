package com.example.lab8.strategy;

import org.springframework.stereotype.Component;

/** ไม่มีส่วนลด — ราคาสุทธิ = ราคาปกติ */
@Component
public class NoDiscountStrategy implements DiscountStrategy {

    @Override
    public double applyDiscount(double price) {
        return price;
    }

    @Override
    public String getName() {
        return "NONE";
    }
}
