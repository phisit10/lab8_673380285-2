package com.example.lab8.repository;

import com.example.lab8.model.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * ISP: แยกออกจาก ProductRepository เป็นคนละ interface เพราะ Review
 * มีการ query เฉพาะทางของตัวเอง (เช่น findByProductId) ไม่ควรถูกยัดรวมไว้ใน
 * interface เดียวกับ Product
 */
@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {
    List<Review> findByProductId(Long productId);
}
