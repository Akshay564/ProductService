package com.example.productservice.mappers;

import com.example.productservice.dtos.CategoryRequest;
import com.example.productservice.dtos.CategoryResponse;
import com.example.productservice.dtos.ProductCreateRequest;
import com.example.productservice.dtos.ProductResponse;
import com.example.productservice.models.Category;
import com.example.productservice.models.Product;
import org.springframework.stereotype.Component;

@Component
public class ProductMapper {
    public Product toEntity(ProductCreateRequest dto) {
        Product product = new Product();
        product.setTitle(dto.getTitle());
        product.setDescription(dto.getDescription());
        product.setPrice(dto.getPrice());

        Category category = new Category();
        CategoryRequest dtoCategory = dto.getCategory();
        if (dtoCategory == null) {
            throw new IllegalArgumentException("Category id is null");
        }
        category.setTitle(dto.getCategory().getTitle());
        product.setCategory(category);

        return product;
    }

    public ProductResponse toDto(Product entity) {
        ProductResponse dto = new ProductResponse();
        dto.setId(entity.getId());
        dto.setTitle(entity.getTitle());
        dto.setDescription(entity.getDescription());
        dto.setPrice(entity.getPrice());

        CategoryResponse categoryDto = new CategoryResponse();
        categoryDto.setId(entity.getCategory().getId());
        categoryDto.setTitle(entity.getCategory().getTitle());

        dto.setCategory(categoryDto);

        return dto;
    }

    public void updateEntity(ProductCreateRequest dto, Product entity) {
        entity.setTitle(dto.getTitle());
        entity.setDescription(dto.getDescription());
        entity.setPrice(dto.getPrice());

        entity.getCategory().setTitle(dto.getCategory().getTitle());
    }
}
