package com.accenture.foodflow.common.exception.handler;

import com.accenture.foodflow.common.exception.exceptions.FoodBadRequestException;
import com.accenture.foodflow.common.exception.exceptions.FoodNotFoundException;
import com.accenture.foodflow.common.exception.exceptions.FoodReservationBadRequestException;
import com.accenture.foodflow.common.exception.exceptions.FoodReservationNotFoundException;
import com.accenture.foodflow.common.exception.exceptions.InvalidPageException;
import com.accenture.foodflow.common.exception.exceptions.OffsetRequestException;
import com.accenture.foodflow.common.exception.exceptions.UserBadRequestException;
import com.accenture.foodflow.common.exception.exceptions.UserNotAuthorizedException;
import com.accenture.foodflow.common.exception.exceptions.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return errors;
    }

    @ExceptionHandler
    public ResponseEntity<Map<String, String>> handleInvalidUserException(EntityNotFoundException ex) {
        return buildErrorResponse(HttpStatus.NOT_FOUND, ex.getMessage());
    }

    @ExceptionHandler
    public ResponseEntity<Map<String, String>> handleFoodNotFoundException(FoodNotFoundException ex) {
        return buildErrorResponse(HttpStatus.NOT_FOUND, ex.getMessage());
    }

    @ExceptionHandler
    public ResponseEntity<Map<String, String>> handleFoodBadRequestException(FoodBadRequestException ex) {
        return buildErrorResponse(HttpStatus.BAD_REQUEST, ex.getMessage());
    }

    @ExceptionHandler
    public ResponseEntity<Map<String, String>> handleUserNotAuthorizedException(UserNotAuthorizedException ex) {
        return buildErrorResponse(HttpStatus.FORBIDDEN, ex.getMessage());
    }

    @ExceptionHandler
    public ResponseEntity<Map<String, String>> handleInvalidPageException(InvalidPageException ex) {
        return buildErrorResponse(HttpStatus.BAD_REQUEST, ex.getMessage());
    }

    @ExceptionHandler
    public ResponseEntity<Map<String, String>> handleFoodReservationBadRequestException(FoodReservationBadRequestException ex) {
        return buildErrorResponse(HttpStatus.BAD_REQUEST, ex.getMessage());
    }

    @ExceptionHandler
    public ResponseEntity<Map<String, String>> handleUserBadRequestException(UserBadRequestException ex) {
        return buildErrorResponse(HttpStatus.BAD_REQUEST, ex.getMessage());
    }

    @ExceptionHandler
    public ResponseEntity<Map<String, String>> handleFoodReservationNotFoundException(FoodReservationNotFoundException ex) {
        return buildErrorResponse(HttpStatus.NOT_FOUND, ex.getMessage());
    }

    @ExceptionHandler
    public ResponseEntity<Map<String, String>> handleInvalidPageException(OffsetRequestException ex) {
        return buildErrorResponse(HttpStatus.BAD_REQUEST, ex.getMessage());
    }


    private ResponseEntity<Map<String, String>> buildErrorResponse(HttpStatus status, String message) {
        Map<String, String> response = new HashMap<>();
        response.put("error", message);
        return ResponseEntity.status(status)
                .contentType(MediaType.APPLICATION_JSON)
                .body(response);
    }

}