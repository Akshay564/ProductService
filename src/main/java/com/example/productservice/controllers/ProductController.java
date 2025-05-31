package com.example.productservice.controllers;

import com.example.productservice.models.Product;
import com.example.productservice.services.ProductService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("products")
public class ProductController {

    ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("{id}")
    public Product getProductById(@PathVariable Long id) {
        return  productService.getProductById(id);
    }

    @GetMapping()
    public List<Product> getAllProducts() {
        return productService.getAllProducts();
    }

    @PutMapping("{id}")
    public Product updateProduct(@PathVariable Long id, @RequestBody Product product) {
        return  productService.replaceProduct(id, product);
    }
}
