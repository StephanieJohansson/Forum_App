package com.example.Forum_App;

//importing the necessary classes to handle exceptions and HTTP status codes
import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

//importing utility classes for working with maps
import java.util.HashMap;
import java.util.Map;

//annotation to define global exception handling for all controllers
@RestControllerAdvice
public class MyExceptionHandler {

    //validation errors handler. Triggered when MethodArgumentNotValidException. Because you're a teapot <3
    @ResponseStatus(HttpStatus.I_AM_A_TEAPOT)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String,String> handleInvalidArguments(MethodArgumentNotValidException exceptions){
        //map to store field errors
        Map<String,String> errorList = new HashMap<>();
        //iterates over all field errors
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