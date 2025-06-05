package com.example.productservice.services;

import com.example.productservice.exceptions.ProductNotFoundException;
import com.example.productservice.models.Category;
import com.example.productservice.models.Product;
import com.example.productservice.projections.ProductTitleAndDescription;
import com.example.productservice.repos.ProductRepo;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SelfProductService implements  ProductService{
    private final CategoryService categoryService;
    ProductRepo productRepo;

    public SelfProductService(ProductRepo productRepo, CategoryService categoryService) {
        this.productRepo = productRepo;
        this.categoryService = categoryService;
    }

    @Override
    public Product getProductById(Long id) throws ProductNotFoundException {
        ProductTitleAndDescription productTitleAndDescription = productRepo.getProductTitleAndDescription(id);
        System.out.println(productTitleAndDescription.getTitle() + " " + productTitleAndDescription.getDescription());
        return productRepo.findById(id).get();
    }

    @Override
    public List<Product> getAllProducts() {
        return List.of();
    }

    @Override
    public Product replaceProduct(Long id, Product product) {
        return null;
    }

    @Override
    public Product createProduct(Product product) {
        Category category = product.getCategory();
        if(category.getId() == null){
            Category savedCategory = categoryService.setCategory(category);
            product.setCategory(savedCategory);
        } else {
            //Check whether it is valid or not
        }
        return  productRepo.save(product);
    }

    @Override
    public Product deleteProduct(Long id) throws ProductNotFoundException {
        return null;
    }
}
