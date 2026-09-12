package com.example.lab8.strategy;

import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * DiscountContext — จุดกลางที่เลือกใช้ DiscountStrategy ที่เหมาะสม
 * OCP: ถ้าต้องเพิ่มโปรโมชั่นใหม่ (เช่น ClearanceStrategy) แค่สร้าง class ใหม่ที่ implement
 * DiscountStrategy แล้วลงทะเบียนที่นี่ ไม่ต้องแก้ ProductService หรือ Product เลย
 * DIP: ProductService เรียกใช้ DiscountContext ผ่าน abstraction (DiscountStrategy) เท่านั้น
 */
@Component
public class DiscountContext {

    private final Map<String, DiscountStrategy> strategies = new HashMap<>();

    // Spring จะ inject list ของทุก Bean ที่ implement DiscountStrategy มาให้อัตโนมัติ
    public DiscountContext(List<DiscountStrategy> strategyList) {
        for (DiscountStrategy strategy : strategyList) {
            strategies.put(strategy.getName(), strategy);
        }
    }

    public double calculatePrice(double price, String discountType) {
        DiscountStrategy strategy = strategies.getOrDefault(discountType, strategies.get("NONE"));
        if (strategy == null) {
            return price;
        }
        return strategy.applyDiscount(price);
    }
}
