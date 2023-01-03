package com.demo.emailservice.advice;

import com.demo.emailservice.dto.Problem;
import com.demo.emailservice.exception.EmailException;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.ArrayList;
import java.util.List;

import static java.util.Optional.of;
import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

@ControllerAdvice
public class ExceptionAdvice {
    @ExceptionHandler(value = EmailException.class)
    ResponseEntity<Problem> handleEmailException(EmailException e) {
        Problem problem = new Problem();
        problem.setMessage(e.getMessage());
        problem.setHttpStatusCode(INTERNAL_SERVER_ERROR.value());
        return ResponseEntity.of(of(problem));
    }

    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    ResponseEntity<List<Problem>> handleArgumentNotValidException(MethodArgumentNotValidException e) {
        List<Problem> problems = new ArrayList<>();
        e.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            Problem problem = new Problem();
            problem.setMessage(errorMessage);
            problem.setField(fieldName);
            problem.setHttpStatusCode(BAD_REQUEST.value());
            problems.add(problem);
        });
        return ResponseEntity.badRequest().body(problems);
    }
}
