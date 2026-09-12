package com.example.lab8.strategy;

import org.springframework.stereotype.Component;

/** ส่วนลดสมาชิก 10% */
@Component
public class MemberDiscountStrategy implements DiscountStrategy {

    private static final double RATE = 0.10;

    @Override
    public double applyDiscount(double price) {
        return price - (price * RATE);
    }

    @Override
    public String getName() {
        return "MEMBER";
    }
}
