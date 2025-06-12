package com.example.productservice.dtos;

import jakarta.validation.Valid;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductCreateRequest {
    @NotBlank(message = "Description is required")
    private String description;

    @DecimalMin(value = "0.2")
    private Double price;

    @NotBlank(message = "Title is required")
    private String title;

    @Valid
    private CategoryRequest category;

    private Long id;
}
