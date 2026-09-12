package com.example.lab8.service;

import com.example.lab8.model.Product;
import com.example.lab8.model.Review;
import com.example.lab8.repository.ProductRepository;
import com.example.lab8.strategy.DiscountContext;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

/**
 * ProductService — SRP: รับผิดชอบเฉพาะ business logic ของ Product
 * (ไม่ยุ่งกับ HTTP เหมือน Controller และไม่ยุ่งกับ SQL เหมือน Repository)
 *
 * DIP: ขึ้นกับ ProductRepository (interface) และ DiscountContext (abstraction
 * เหนือ DiscountStrategy) ผ่าน Constructor Injection แทนการ "new" object เอง
 */
@Service
public class ProductService {

    private final ProductRepository productRepository;
    private final DiscountContext discountContext;

    // Constructor Injection — Spring จะ inject bean ทั้งสองให้อัตโนมัติ
    public ProductService(ProductRepository productRepository, DiscountContext discountContext) {
        this.productRepository = productRepository;
        this.discountContext = discountContext;
    }

    public List<Product> findAll() {
        return productRepository.findAll();
    }

    public Optional<Product> findById(Long id) {
        return productRepository.findById(id);
    }

    public Product save(Product product) {
        cleanBlankReviews(product);
        linkReviews(product);
        return productRepository.save(product);
    }

    public Product update(Long id, Product incoming) {
        Product existing = productRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("ไม่พบสินค้ารหัส " + id));

        existing.setName(incoming.getName());
        existing.setCategory(incoming.getCategory());
        existing.setBrand(incoming.getBrand());
        existing.setStock(incoming.getStock());
        existing.setPrice(incoming.getPrice());
        existing.setDiscountType(incoming.getDiscountType());

        // 1:1 — อัปเดต ProductDetail ที่มีอยู่แทนการสร้างใหม่ทุกครั้ง
        if (existing.getDetail() == null) {
            existing.setDetail(incoming.getDetail());
        } else if (incoming.getDetail() != null) {
            existing.getDetail().setDescription(incoming.getDetail().getDescription());
            existing.getDetail().setWarranty(incoming.getDetail().getWarranty());
            existing.getDetail().setWeight(incoming.getDetail().getWeight());
            existing.getDetail().setDimensions(incoming.getDetail().getDimensions());
            existing.getDetail().setManufacturedCountry(incoming.getDetail().getManufacturedCountry());
        }

        return productRepository.save(existing);
    }

    public void deleteById(Long id) {
        productRepository.deleteById(id);
    }

    /** ตัวอย่างการใช้ Strategy Pattern แบบ pluggable ผ่าน DiscountContext */
    public double calculateFinalPrice(Product product) {
        return discountContext.calculatePrice(product.getPrice(), product.getDiscountType());
    }

    // ── helpers ──

    private void cleanBlankReviews(Product product) {
        if (product.getReviews() != null) {
            product.getReviews().removeIf(Review::isBlank);
        }
    }

    private void linkReviews(Product product) {
        if (product.getReviews() != null) {
            for (Review review : product.getReviews()) {
                review.setProduct(product);
                if (review.getReviewDate() == null) {
                    review.setReviewDate(LocalDate.now());
                }
            }
        }
    }
}
