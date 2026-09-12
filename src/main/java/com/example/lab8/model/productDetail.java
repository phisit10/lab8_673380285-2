package com.example.lab8.model;

import jakarta.persistence.*;

/**
 * ProductDetail — ข้อมูลเสริมของสินค้า
 * SRP: แยกข้อมูลรายละเอียด/สเปกออกจาก Product หลัก เพื่อให้แต่ละ Entity
 * มีหน้าที่รับผิดชอบเดียว (Product = ข้อมูลหลักสำหรับขาย, ProductDetail = ข้อมูลเสริม)
 *
 * ความสัมพันธ์ 1:1 — ฝั่งนี้เป็น "inverse side" (mappedBy) เพราะ FK (detail_id)
 * ถูกเก็บไว้ที่ตาราง products (owning side)
 */
@Entity
@Table(name = "product_details")
public class ProductDetail {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 1000)
    private String description;

    private String warranty;

    private Double weight;

    private String dimensions;

    private String manufacturedCountry;

    // inverse side ของ 1:1 — ไม่ถือ FK จริง แค่ชี้กลับไปหา Product
    @OneToOne(mappedBy = "detail")
    private Product product;

    public ProductDetail() {
    }

    public ProductDetail(String description, String warranty, Double weight,
                          String dimensions, String manufacturedCountry) {
        this.description = description;
        this.warranty = warranty;
        this.weight = weight;
        this.dimensions = dimensions;
        this.manufacturedCountry = manufacturedCountry;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getWarranty() {
        return warranty;
    }

    public void setWarranty(String warranty) {
        this.warranty = warranty;
    }

    public Double getWeight() {
        return weight;
    }

    public void setWeight(Double weight) {
        this.weight = weight;
    }

    public String getDimensions() {
        return dimensions;
    }

    public void setDimensions(String dimensions) {
        this.dimensions = dimensions;
    }

    public String getManufacturedCountry() {
        return manufacturedCountry;
    }

    public void setManufacturedCountry(String manufacturedCountry) {
        this.manufacturedCountry = manufacturedCountry;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }
}
