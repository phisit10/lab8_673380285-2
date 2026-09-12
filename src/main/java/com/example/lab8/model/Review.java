package com.example.lab8.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import java.time.LocalDate;

/**
 * Review — รีวิวของสินค้าแต่ละชิ้น
 * OCP: เพิ่ม Entity ใหม่นี้เข้ามาได้โดยไม่ต้องแก้โค้ดภายใน Product เลย
 * (Product แค่ประกาศ List<Review> เฉยๆ)
 *
 * ความสัมพันธ์ 1:N — ฝั่งนี้เป็น "owning side" (@ManyToOne) เพราะ FK (product_id)
 * ถูกเก็บไว้ที่ตาราง reviews เสมอ (FK อยู่ฝั่ง Many)
 */
@Entity
@Table(name = "reviews")
public class Review {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String reviewer;

    private Integer rating;

    @Column(length = 1000)
    private String comment;

    private LocalDate reviewDate;

    @ManyToOne
    @JoinColumn(name = "product_id")
    @JsonIgnore
    private Product product;

    public Review() {
    }

    public Review(String reviewer, Integer rating, String comment, LocalDate reviewDate) {
        this.reviewer = reviewer;
        this.rating = rating;
        this.comment = comment;
        this.reviewDate = reviewDate;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getReviewer() {
        return reviewer;
    }

    public void setReviewer(String reviewer) {
        this.reviewer = reviewer;
    }

    public Integer getRating() {
        return rating;
    }

    public void setRating(Integer rating) {
        this.rating = rating;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public LocalDate getReviewDate() {
        return reviewDate;
    }

    public void setReviewDate(LocalDate reviewDate) {
        this.reviewDate = reviewDate;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    /** ช่วยให้แน่ใจว่าฝั่ง 1 (Product) กับฝั่ง N (Review) sync กันเสมอ */
    public boolean isBlank() {
        return (reviewer == null || reviewer.isBlank())
                && (comment == null || comment.isBlank());
    }
}
