package com.fatec.sasbackend.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;

@ControllerAdvice
@ResponseBody
public class GlobalHandlerException {

//    @ExceptionHandler(MethodArgumentNotValidException.class)
//    protected ResponseEntity<DefaultError> validationExceptionHandler(
//            MethodArgumentNotValidException ex,
//            HttpServletRequest request
//    ){
//        HashMap<String, String> validationErrors = new HashMap<>();
//
//        ex.getBindingResult().getAllErrors().forEach(e -> {
//            String error = ((FieldError)e).getField();
//            String message = e.getDefaultMessage();
//            validationErrors.put(error, message);
//        });
//
//        DefaultError error = new DefaultError();
//
//        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
//    }
}
