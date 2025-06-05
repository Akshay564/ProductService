package com.example.productservice.services;

import com.example.productservice.models.Category;
import com.example.productservice.repos.CategoryRepo;
import org.springframework.stereotype.Service;

@Service
public class CategoryServiceImpl implements CategoryService {
    CategoryRepo categoryRepo;

    public CategoryServiceImpl(CategoryRepo categoryRepo) {
        this.categoryRepo = categoryRepo;
    }

    @Override
    public Category getCategory(Long id) {
        return null;
    }

    @Override
    public Category setCategory(Category category) {
        return categoryRepo.save(category);
    }
}
