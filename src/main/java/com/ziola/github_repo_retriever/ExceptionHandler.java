package com.ziola.github_repo_retriever;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import static org.springframework.http.HttpStatus.NOT_FOUND;

@RestControllerAdvice
class ExceptionHandler {

    @org.springframework.web.bind.annotation.ExceptionHandler(UserNotFoundException.class)
    ResponseEntity<ErrorResponse> handleUserNotFoundException(UserNotFoundException ex) {
        var response = new ErrorResponse(NOT_FOUND.value(), ex.getMessage());
        return new ResponseEntity<>(response, NOT_FOUND);
    }
}
