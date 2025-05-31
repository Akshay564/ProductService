package com.example.productservice.exceptions;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductNotFoundException extends Exception {
    private  Long id;

    public ProductNotFoundException(Long id, String message) {
        super(message);
        this.id = id;
    }
}
