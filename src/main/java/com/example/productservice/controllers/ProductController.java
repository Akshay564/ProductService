package com.example.productservice.controllers;

import com.example.productservice.dtos.ProductCreateRequest;
import com.example.productservice.dtos.ProductResponse;
import com.example.productservice.exceptions.ProductNotFoundException;
import com.example.productservice.mappers.ProductMapper;
import com.example.productservice.models.Product;
import com.example.productservice.services.ProductService;
import com.example.productservice.services.TokenService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("products")
public class ProductController {

    ProductService productService;
    ProductMapper productMapper;
    TokenService tokenService;

    public ProductController(@Qualifier("selfProductService") ProductService productService, ProductMapper productMapper, TokenService tokenService) {
        this.productService = productService;
        this.productMapper = productMapper;
        this.tokenService = tokenService;
    }

    @GetMapping("{id}")
    public ProductResponse getProductById(@RequestHeader("Token") String token, @PathVariable Long id) throws ProductNotFoundException, ResponseStatusException {
        if (!tokenService.validateToken(token)) {
            throw new org.springframework.web.server.ResponseStatusException(HttpStatus.UNAUTHORIZED, "User is not authorized to access this resource");
        }
        Product product = productService.getProductById(id);
        return productMapper.toDto(product);
    }

    @GetMapping()
    public List<ProductResponse> getAllProducts() {
        return productService.getAllProducts().stream()
                .map(productMapper::toDto)
                .toList();
    }

    @PutMapping("{id}")
    public ResponseEntity<ProductResponse> updateProduct(@PathVariable Long id, @Valid @RequestBody ProductCreateRequest request) throws ProductNotFoundException {
        Product saved = productService.replaceProduct(id, request);
        ProductResponse dto = productMapper.toDto(saved);
        return ResponseEntity.ok().body(dto);
    }

    @PostMapping
    public ResponseEntity<ProductResponse> createProduct(@Valid @RequestBody ProductCreateRequest request) {
        Product savedProduct = productService.createProduct(request);
        ProductResponse dto = productMapper.toDto(savedProduct);
        return ResponseEntity.status(HttpStatus.CREATED).body(dto);
    }

    @DeleteMapping("{id}")
    public ProductResponse deleteProduct(@PathVariable Long id) throws ProductNotFoundException {
        return productMapper.toDto(productService.deleteProduct(id));
    }
}
