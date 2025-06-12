package com.example.productservice.models;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CustomError {
    private String message;
    private int status;
}
