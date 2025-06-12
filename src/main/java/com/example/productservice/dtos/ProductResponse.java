package com.example.productservice.dtos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductResponse {
    private Long id;
    private String description;
    private Double price;
    private String title;

    private CategoryResponse category;
}
