package com.evolution.food.api.execeptionhandler;

import com.evolution.food.api.domain.exception.BusinessException;
import com.evolution.food.api.domain.exception.EntityNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;

@ControllerAdvice
public class ApiExceptionHandler {

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<?> handleEntityNotFoundException(EntityNotFoundException exception) {

        Problem problem = Problem.builder()
                .dateTime(LocalDateTime.now())
                .massage(exception.getMessage()).build();

        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(problem);
    }

    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<?> handleBusinessException(BusinessException exception) {

        Problem problem = Problem.builder()
                .dateTime(LocalDateTime.now())
                .massage(exception.getMessage()).build();

        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(problem);
    }

    @ExceptionHandler(HttpMediaTypeNotSupportedException.class)
    public ResponseEntity<?> handleHttpMediaTypeNotSupportedException() {

        Problem problem = Problem.builder()
                .dateTime(LocalDateTime.now())
                .massage("Tipo de midia nao suportado.").build();

        return ResponseEntity.status(HttpStatus.UNSUPPORTED_MEDIA_TYPE)
                .body(problem);
    }
}
