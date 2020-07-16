package com.github.gustavovitor.exceptions;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.validation.BindingResult;

@Getter
@AllArgsConstructor
public class ValidationException extends RuntimeException {
    private BindingResult bindingResult;
}
