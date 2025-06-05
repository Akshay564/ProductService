package com.example.productservice.services;

import com.example.productservice.models.Category;

public interface CategoryService {
    Category getCategory(Long id);

    Category setCategory(Category category);
}
