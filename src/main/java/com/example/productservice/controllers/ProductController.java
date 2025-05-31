package com.example.productservice.controllers;

import com.example.productservice.exceptions.ProductNotFoundException;
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
    public Product getProductById(@PathVariable Long id) throws ProductNotFoundException {
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

    @PostMapping()
    public Product createProduct(@RequestBody Product product) {
        return productService.createProduct(product);
    }

    @DeleteMapping("{id}")
    public Product deleteProduct(@PathVariable Long id) throws ProductNotFoundException {
        return  productService.deleteProduct(id);
    }
}
