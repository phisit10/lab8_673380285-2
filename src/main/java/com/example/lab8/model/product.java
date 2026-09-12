package com.example.lab8.model;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Product — Entity หลักของสินค้า
 *
 * ความสัมพันธ์ 1:1 กับ ProductDetail — Product เป็น "owning side"
 * (เก็บ FK: detail_id) เพราะเวลา query สินค้าไม่จำเป็นต้องดึง detail เสมอไป
 * แยกออกมาเพื่อ SRP (Product = ข้อมูลซื้อขาย, ProductDetail = ข้อมูลเสริม/สเปก)
 *
 * ความสัมพันธ์ 1:N กับ Review — Product ไม่ถือ FK (FK อยู่ที่ Review.product_id)
 * ใช้ mappedBy ชี้ไปยัง field "product" ใน Review
 */
@Entity
@Table(name = "products")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    private String category;

    private String brand;

    private Integer stock;

    private Double price;

    /** ค่าที่เป็นไปได้: NONE, MEMBER, SEASONAL — ใช้คู่กับ Strategy Pattern (DiscountContext) */
    private String discountType = "NONE";

    // ── 1:1 กับ ProductDetail — Product เป็นฝั่งเจ้าของ (มี FK detail_id) ──
    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "detail_id", referencedColumnName = "id")
    private ProductDetail detail;

    // ── 1:N กับ Review — FK อยู่ฝั่ง Review เสมอ ──
    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    private List<Review> reviews = new ArrayList<>();

    public Product() {
    }

    public Product(String name, String category, String brand, Integer stock,
                    Double price, String discountType) {
        this.name = name;
        this.category = category;
        this.brand = brand;
        this.stock = stock;
        this.price = price;
        this.discountType = discountType;
    }

    // ── helper เพื่อรักษาความสัมพันธ์ทั้งสองฝั่งให้ sync กันเสมอ (1:N) ──
    public void addReview(Review review) {
        reviews.add(review);
        review.setProduct(this);
    }

    public void setDetail(ProductDetail detail) {
        this.detail = detail;
        if (detail != null) {
            detail.setProduct(this);
        }
    }

    /**
     * ราคาสุทธิหลังหักส่วนลด — คำนวณตรงตาม logic เดียวกับ DiscountStrategy
     * (ตัวจริงของการคำนวณส่วนลดแบบ pluggable อยู่ใน DiscountContext/service layer;
     * getter นี้เป็นเพียง convenience สำหรับแสดงผลในตาราง Thymeleaf)
     */
    @Transient
    public Double getDiscountedPrice() {
        if (price == null) {
            return null;
        }
        if ("MEMBER".equalsIgnoreCase(discountType)) {
            return price - (price * 0.10);
        } else if ("SEASONAL".equalsIgnoreCase(discountType)) {
            return price - (price * 0.20);
        }
        return price;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public Integer getStock() {
        return stock;
    }

    public void setStock(Integer stock) {
        this.stock = stock;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getDiscountType() {
        return discountType;
    }

    public void setDiscountType(String discountType) {
        this.discountType = discountType;
    }

    public ProductDetail getDetail() {
        return detail;
    }

    public List<Review> getReviews() {
        return reviews;
    }

    public void setReviews(List<Review> reviews) {
        this.reviews = reviews;
    }
}
