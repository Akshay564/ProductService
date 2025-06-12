package com.example.productservice.controllers;

import com.example.productservice.dtos.ProductCreateRequest;
import com.example.productservice.dtos.ProductResponse;
import com.example.productservice.exceptions.ProductNotFoundException;
import com.example.productservice.mappers.ProductMapper;
import com.example.productservice.models.Product;
import com.example.productservice.services.ProductService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("products")
public class ProductController {

    ProductService productService;
    ProductMapper productMapper;

    public ProductController(@Qualifier("selfProductService") ProductService productService, ProductMapper productMapper) {
        this.productService = productService;
        this.productMapper = productMapper;
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
    public ResponseEntity<ProductResponse> updateProduct(@PathVariable Long id, @Valid @RequestBody ProductCreateRequest request) throws ProductNotFoundException {
        Product saved = productService.replaceProduct(id, request);
        ProductResponse dto = productMapper.toDto(saved);
        return  ResponseEntity.ok().body(dto);
    }

    @PostMapping
    public ResponseEntity<ProductResponse> createProduct(@Valid @RequestBody ProductCreateRequest request) {
        Product savedProduct = productService.createProduct(request);
        ProductResponse dto = productMapper.toDto(savedProduct);
        return ResponseEntity.status(HttpStatus.CREATED).body(dto);
    }

    @DeleteMapping("{id}")
    public Product deleteProduct(@PathVariable Long id) throws ProductNotFoundException {
        return  productService.deleteProduct(id);
    }
}
