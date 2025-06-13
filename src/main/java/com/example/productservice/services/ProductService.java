package com.example.productservice.services;

import com.example.productservice.dtos.ProductCreateRequest;
import com.example.productservice.exceptions.ProductNotFoundException;
import com.example.productservice.models.Product;

import java.util.List;

public interface ProductService {
    Product getProductById(Long id) throws ProductNotFoundException;

    List<Product> getAllProducts();

    Product replaceProduct(Long id, ProductCreateRequest product) throws ProductNotFoundException;

    Product createProduct(ProductCreateRequest product);

    Product deleteProduct(Long id) throws ProductNotFoundException;
}
