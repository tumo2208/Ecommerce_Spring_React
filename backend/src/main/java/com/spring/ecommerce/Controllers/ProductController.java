package com.spring.ecommerce.Controllers;

import com.spring.ecommerce.DTOs.ProductDTO;
import com.spring.ecommerce.DTOs.Response;
import com.spring.ecommerce.Services.Interfaces.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/product")
public class ProductController {
    @Autowired
    private ProductService productService;

    @PostMapping("/create")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Response> createProduct(@RequestBody ProductDTO productDTO) {
        try {
            if (productDTO.getCategoryId() == null || productDTO.getName() == null ||
                productDTO.getDescription() == null || productDTO.getPrice() == null ||
                productDTO.getImage() == null) {
                Response response = new Response();
                response.setStatus(400);
                response.setMessage("All fields are required");
                return ResponseEntity.badRequest().body(response);
            }
            Response response = productService.createProduct(productDTO.getCategoryId(),
                                                            productDTO.getName(),
                                                            productDTO.getDescription(),
                                                            productDTO.getPrice(),
                                                            productDTO.getImage());

            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Response response = new Response();
            response.setStatus(500);
            response.setMessage("Failed to create product: " + e.getMessage());
            return ResponseEntity.status(500).body(response);
        }
    }

    @PutMapping("/update/{productId}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Response> updateProduct(@PathVariable Long productId,
                                                  @RequestBody ProductDTO productDTO) {
        try {
            Response response = productService.updateProduct(productId,
                                                            productDTO.getCategoryId(),
                                                            productDTO.getName(),
                                                            productDTO.getDescription(),
                                                            productDTO.getPrice(),
                                                            productDTO.getImage());

            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Response response = new Response();
            response.setStatus(500);
            response.setMessage("Failed to update product: " + e.getMessage());
            return ResponseEntity.status(500).body(response);
        }
    }

    @DeleteMapping("/delete/{productId}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Response> deleteProduct(@PathVariable Long productId) {
        try {
            Response response = productService.deleteProduct(productId);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Response response = new Response();
            response.setStatus(500);
            response.setMessage("Failed to delete product: " + e.getMessage());
            return ResponseEntity.status(500).body(response);
        }
    }

    @GetMapping("/all")
    public ResponseEntity<Response> getAllProducts() {
        try {
            Response response = productService.getAllProducts();
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Response response = new Response();
            response.setStatus(500);
            response.setMessage("Failed to retrieve products: " + e.getMessage());
            return ResponseEntity.status(500).body(response);
        }
    }

    @GetMapping("/category/{categoryId}")
    public ResponseEntity<Response> getProductsByCategory(@PathVariable Long categoryId) {
        try {
            Response response = productService.getProductsByCategory(categoryId);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Response response = new Response();
            response.setStatus(500);
            response.setMessage("Failed to retrieve products by category: " + e.getMessage());
            return ResponseEntity.status(500).body(response);
        }
    }

    @GetMapping("/search")
    public ResponseEntity<Response> searchProducts(@RequestParam String query) {
        try {
            Response response = productService.searchProducts(query);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Response response = new Response();
            response.setStatus(500);
            response.setMessage("Failed to search products: " + e.getMessage());
            return ResponseEntity.status(500).body(response);
        }
    }
}
