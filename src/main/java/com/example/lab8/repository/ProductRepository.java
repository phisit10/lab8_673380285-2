package com.example.lab8.repository;

import com.example.lab8.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * DIP: ProductService ขึ้นกับ interface นี้ (abstraction) แทนที่จะขึ้นกับ
 * implementation ของ JPA โดยตรง — Spring Data JPA จะสร้าง implementation ให้เองตอน runtime
 */
@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
}
