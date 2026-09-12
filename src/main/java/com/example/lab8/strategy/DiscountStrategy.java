package com.example.lab8.strategy;

/**
 * DiscountStrategy — Strategy Pattern
 * ISP: interface เล็ก มีแค่ method เดียวที่จำเป็น ไม่บังคับให้ implementation
 * ต้อง implement อะไรที่ไม่เกี่ยวข้อง
 * DIP: ProductService / Product จะขึ้นกับ interface นี้ ไม่ใช่ implementation จริง
 */
public interface DiscountStrategy {
    double applyDiscount(double price);

    String getName();
}
