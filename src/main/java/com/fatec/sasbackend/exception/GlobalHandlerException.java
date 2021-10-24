package com.fatec.sasbackend.exception;

import com.fatec.sasbackend.model.error.DefaultResponseError;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.HashMap;

@ControllerAdvice
@ResponseBody
public class GlobalHandlerException {

    //validation handler
    @ExceptionHandler(MethodArgumentNotValidException.class)
    protected ResponseEntity<DefaultResponseError> validationExceptionHandler(
            MethodArgumentNotValidException ex,
            HttpServletRequest request
    ){
        HashMap<String, String> validationErrors = new HashMap<>();

        ex.getBindingResult().getAllErrors().forEach(e -> {
            String error = ((FieldError)e).getField();
            String message = e.getDefaultMessage();
            validationErrors.put(error, message);
        });

        DefaultResponseError error = new DefaultResponseError(
            "Validation",
            HttpStatus.BAD_REQUEST.value(),
            validationErrors,
            LocalDateTime.now(),
            request.getRequestURI()
        );

        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }
}
