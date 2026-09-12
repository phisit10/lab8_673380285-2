package com.example.lab8.strategy;

import org.springframework.stereotype.Component;

/** ส่วนลดเทศกาล 20% */
@Component
public class SeasonalSaleStrategy implements DiscountStrategy {

    private static final double RATE = 0.20;

    @Override
    public double applyDiscount(double price) {
        return price - (price * RATE);
    }

    @Override
    public String getName() {
        return "SEASONAL";
    }
}
