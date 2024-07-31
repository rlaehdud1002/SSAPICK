package com.ssapick.server.core.response;


import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import java.util.HashMap;
import java.util.Map;

public class CustomValidationError {
    private Map<String, String> errors = new HashMap<>();

    public CustomValidationError(BindingResult bindingResult) {
        bindingResult.getAllErrors().forEach(error -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
    }
}
