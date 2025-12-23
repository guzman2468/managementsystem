package com.library.managementsystem;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.validation.FieldError;
import com.library.managementsystem.model.MessageReponse;

import java.util.List;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<MessageReponse> handleValidationException(MethodArgumentNotValidException ex) {
        // Grab the first field error
        FieldError fieldError = ex.getBindingResult().getFieldError();
        String errorMessage = (fieldError != null) ? fieldError.getDefaultMessage() : "Validation error";

        // Wrap it in your custom response
        return new ResponseEntity<>(new MessageReponse(errorMessage), HttpStatus.BAD_REQUEST);
    }
}
