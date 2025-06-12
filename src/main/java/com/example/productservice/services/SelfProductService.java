package com.example.productservice.services;

import com.example.productservice.dtos.ProductCreateRequest;
import com.example.productservice.exceptions.ProductNotFoundException;
import com.example.productservice.mappers.ProductMapper;
import com.example.productservice.models.Category;
import com.example.productservice.models.Product;
import com.example.productservice.repos.CategoryRepo;
import com.example.productservice.repos.ProductRepo;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SelfProductService implements ProductService {
    private final ProductRepo productRepo;
    private final CategoryRepo categoryRepo;
    private final ProductMapper productMapper;

    public SelfProductService(ProductRepo productRepo, ProductMapper productMapper, CategoryRepo categoryRepo) {
        this.productRepo = productRepo;
        this.productMapper = productMapper;
        this.categoryRepo = categoryRepo;
    }

    @Override
    public Product getProductById(Long id) throws ProductNotFoundException {
        return productRepo.findById(id).orElseThrow(() -> new ProductNotFoundException(id, "Product not found with id:" + id));
    }

    @Override
    public List<Product> getAllProducts() {
        return productRepo.findAll();
    }

    @Override
    public Product replaceProduct(Long id, ProductCreateRequest dto) throws ProductNotFoundException {
        Product existing = productRepo.findById(id).orElseThrow(() -> new ProductNotFoundException(id, "Product not found with id:" + id));

        productMapper.updateEntity(dto, existing);

        return productRepo.save(existing);
    }

    @Override
    public Product createProduct(ProductCreateRequest dto)  {
        Product product = productMapper.toEntity(dto);
        Long categoryId = dto.getCategory().getId();
        String categoryTitle = dto.getCategory().getTitle();

        if (categoryId != null) {
            Category category = categoryRepo.findById(categoryId).orElseThrow(() -> new IllegalArgumentException("Category not found"));
            product.setCategory(category);
        } else if (categoryTitle != null && !categoryTitle.isBlank()) {
            Category newCategory = new Category();
            newCategory.setTitle(categoryTitle);
            categoryRepo.save(newCategory);
            product.setCategory(newCategory);
        } else {
            throw new IllegalArgumentException("Either category ID or title must be provided");
        }

        return productRepo.save(product);
    }

    @Override
    public Product deleteProduct(Long id) throws ProductNotFoundException {
        Product existing = productRepo.findById(id).orElseThrow(() -> new ProductNotFoundException(id, "Product not found with id:" + id));
        productRepo.delete(existing);
        return existing;
    }
}
