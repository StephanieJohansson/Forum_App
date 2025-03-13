package com.example.Forum_App;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class MyExceptionHandler {

    @ResponseStatus(HttpStatus.I_AM_A_TEAPOT)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String,String> handleInvalidArguments(MethodArgumentNotValidException exceptions){
        Map<String,String> errorList = new HashMap<>();
        exceptions.getBindingResult()
                .getFieldErrors()
                .forEach (e -> errorList.put(e.getField(),e.getDefaultMessage()));

        return errorList;
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(EntityNotFoundException.class)
    public Map<String,String> handleEntityNotFound(EntityNotFoundException entitys) {
        Map<String,String> errorList = new HashMap<>();
        errorList.put("error: ", entitys.getMessage());
        return errorList;
    }

}