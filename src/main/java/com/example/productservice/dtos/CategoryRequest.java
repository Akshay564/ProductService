package com.example.productservice.dtos;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CategoryRequest {
    @NotBlank(message = "Category title is required")
    private String title;

    private Long id;
}
