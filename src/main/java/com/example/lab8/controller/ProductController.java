package com.example.lab8.controller;

import com.example.lab8.model.Product;
import com.example.lab8.model.ProductDetail;
import com.example.lab8.model.Review;
import com.example.lab8.service.ProductService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 * ProductController — SRP: รับผิดชอบเฉพาะการรับ HTTP request และส่ง response กลับ
 * (แปลง request -> เรียก service -> เลือก view) ไม่มี business logic อยู่ในนี้เลย
 *
 * DIP: ขึ้นกับ ProductService ผ่าน Constructor Injection
 */
@Controller
@RequestMapping("/products")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    // READ — รายการสินค้าทั้งหมด
    @GetMapping
    public String list(Model model) {
        model.addAttribute("products", productService.findAll());
        return "products/list";
    }

    // แสดงฟอร์มเพิ่มสินค้า
    @GetMapping("/add")
    public String showAddForm(Model model) {
        Product product = new Product();
        product.setDetail(new ProductDetail()); // เผื่อ field ผูกกับ *{detail.xxx} ในฟอร์มไม่ error
        product.getReviews().add(new Review()); // เผื่อช่องกรอกรีวิวแรกในฟอร์ม
        model.addAttribute("product", product);
        return "products/add";
    }

    // CREATE — บันทึกสินค้าใหม่ (+ ProductDetail 1:1 + Review 1:N)
    @PostMapping("/save")
    public String save(@ModelAttribute("product") Product product, RedirectAttributes redirectAttributes) {
        productService.save(product);
        redirectAttributes.addFlashAttribute("message", "เพิ่มสินค้า \"" + product.getName() + "\" สำเร็จ");
        return "redirect:/products";
    }

    // แสดงฟอร์มแก้ไข
    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {
        Product product = productService.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("ไม่พบสินค้ารหัส " + id));
        if (product.getDetail() == null) {
            product.setDetail(new ProductDetail()); // กันฟอร์ม error ถ้าสินค้านี้ยังไม่มี ProductDetail
        }
        model.addAttribute("product", product);
        return "products/edit";
    }

    // UPDATE
    @PostMapping("/update/{id}")
    public String update(@PathVariable Long id, @ModelAttribute("product") Product product,
                          RedirectAttributes redirectAttributes) {
        productService.update(id, product);
        redirectAttributes.addFlashAttribute("message", "แก้ไขสินค้า \"" + product.getName() + "\" สำเร็จ");
        return "redirect:/products";
    }

    // แสดงหน้ายืนยันลบ
    @GetMapping("/delete/{id}")
    public String showDeleteConfirm(@PathVariable Long id, Model model) {
        Product product = productService.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("ไม่พบสินค้ารหัส " + id));
        model.addAttribute("product", product);
        return "products/delete";
    }

    // DELETE
    @PostMapping("/delete/{id}")
    public String delete(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        productService.deleteById(id);
        redirectAttributes.addFlashAttribute("message", "ลบสินค้าสำเร็จ");
        return "redirect:/products";
    }
}
