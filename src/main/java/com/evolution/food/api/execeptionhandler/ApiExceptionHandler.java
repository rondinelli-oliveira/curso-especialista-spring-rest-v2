package com.evolution.food.api.execeptionhandler;

import com.evolution.food.api.domain.exception.BusinessException;
import com.evolution.food.api.domain.exception.EntityInUseException;
import com.evolution.food.api.domain.exception.EntityNotFoundException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;

@ControllerAdvice
public class ApiExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<Object> handleEntidadeNaoEncontradaException(
            EntityNotFoundException ex, WebRequest request) {

        return handleExceptionInternal(ex, ex.getMessage(), new HttpHeaders(),
                HttpStatus.NOT_FOUND, request);
    }

    @ExceptionHandler(EntityInUseException.class)
    public ResponseEntity<Object> handleEntidadeEmUsoException(
            EntityInUseException ex, WebRequest request) {

        return handleExceptionInternal(ex, ex.getMessage(), new HttpHeaders(),
                HttpStatus.CONFLICT, request);
    }

    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<Object> handleNegocioException(BusinessException ex, WebRequest request) {
        return handleExceptionInternal(ex, ex.getMessage(), new HttpHeaders(),
                HttpStatus.BAD_REQUEST, request);
    }

    @Override
    protected ResponseEntity<Object> handleHttpRequestMethodNotSupported(
            HttpRequestMethodNotSupportedException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {

        // Customizando a mensagem de metodo nao suportado
        String customMessage = "O metodo de solicitacao 'PATCH' nao e compativel.";

        Problem problem = Problem.builder()
                .dateTime(LocalDateTime.now())
                .message(customMessage)
                .build();

        return new ResponseEntity<>(problem, headers, HttpStatus.METHOD_NOT_ALLOWED);
    }

    @Override
    protected ResponseEntity<Object> handleHttpMediaTypeNotSupported(
            HttpMediaTypeNotSupportedException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {

        // Customizando a mensagem de midia nao suportada
        String customMessage = "Tipo de midia nao suportado.";

        Problem problem = Problem.builder()
                .dateTime(LocalDateTime.now())
                .message(customMessage)
                .build();

        return new ResponseEntity<>(problem, headers, HttpStatus.UNSUPPORTED_MEDIA_TYPE);
    }

    @Override
    protected ResponseEntity<Object> handleExceptionInternal(
            Exception ex, Object body, HttpHeaders headers, HttpStatusCode statusCode, WebRequest request) {
        body = Problem.builder()
                .dateTime(LocalDateTime.now())
                .message(ex.getMessage()).build();
        return super.handleExceptionInternal(ex, body, headers, statusCode, request);
    }

}


//package com.evolution.food.api.execeptionhandler;
//
//import com.evolution.food.api.domain.exception.BusinessException;
//import com.evolution.food.api.domain.exception.EntityInUseException;
//import com.evolution.food.api.domain.exception.EntityNotFoundException;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.HttpMediaTypeNotSupportedException;
//import org.springframework.web.bind.annotation.ControllerAdvice;
//import org.springframework.web.bind.annotation.ExceptionHandler;
//
//import java.time.LocalDateTime;
//
//@ControllerAdvice
//public class ApiExceptionHandler {
//
//    @ExceptionHandler(EntityNotFoundException.class)
//    public ResponseEntity<?> handleEntityNotFoundException(EntityNotFoundException exception) {
//
//        Problem problem = Problem.builder()
//                .dateTime(LocalDateTime.now())
//                .message(exception.getMessage()).build();
//
//        return ResponseEntity.status(HttpStatus.NOT_FOUND)
//                .body(problem);
//    }
//
//    @ExceptionHandler(BusinessException.class)
//    public ResponseEntity<?> handleBusinessException(BusinessException exception) {
//
//        Problem problem = Problem.builder()
//                .dateTime(LocalDateTime.now())
//                .message(exception.getMessage()).build();
//
//        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
//                .body(problem);
//    }
//
//    @ExceptionHandler(HttpMediaTypeNotSupportedException.class)
//    public ResponseEntity<?> handleHttpMediaTypeNotSupportedException() {
//
//        Problem problem = Problem.builder()
//                .dateTime(LocalDateTime.now())
//                .message("Tipo de midia nao suportado.").build();
//
//        return ResponseEntity.status(HttpStatus.UNSUPPORTED_MEDIA_TYPE)
//                .body(problem);
//    }
//
//    @ExceptionHandler(EntityInUseException.class)
//    public ResponseEntity<?> handleEntityInUseException(EntityInUseException exception) {
//
//        Problem problem = Problem.builder()
//                .dateTime(LocalDateTime.now())
//                .message(exception.getMessage()).build();
//
//        return ResponseEntity.status(HttpStatus.CONFLICT)
//                .body(problem);
//    }
//}


//VERSAO DO CURSO
//package com.algaworks.algafood.api.exceptionhandler;
//
//import java.time.LocalDateTime;
//
//import org.springframework.http.HttpHeaders;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.ControllerAdvice;
//import org.springframework.web.bind.annotation.ExceptionHandler;
//import org.springframework.web.context.request.WebRequest;
//import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
//
//import com.algaworks.algafood.domain.exception.EntidadeEmUsoException;
//import com.algaworks.algafood.domain.exception.EntidadeNaoEncontradaException;
//import com.algaworks.algafood.domain.exception.NegocioException;
//
//@ControllerAdvice
//public class ApiExceptionHandler extends ResponseEntityExceptionHandler {
//
//    @ExceptionHandler(EntidadeNaoEncontradaException.class)
//    public ResponseEntity<?> tratarEntidadeNaoEncontradaException(
//            EntidadeNaoEncontradaException ex, WebRequest request) {
//
//        return handleExceptionInternal(ex, ex.getMessage(), new HttpHeaders(),
//                HttpStatus.NOT_FOUND, request);
//    }
//
//    @ExceptionHandler(EntidadeEmUsoException.class)
//    public ResponseEntity<?> tratarEntidadeEmUsoException(
//            EntidadeEmUsoException ex, WebRequest request) {
//
//        return handleExceptionInternal(ex, ex.getMessage(), new HttpHeaders(),
//                HttpStatus.CONFLICT, request);
//    }
//
//    @ExceptionHandler(NegocioException.class)
//    public ResponseEntity<?> tratarNegocioException(NegocioException ex, WebRequest request) {
//        return handleExceptionInternal(ex, ex.getMessage(), new HttpHeaders(),
//                HttpStatus.BAD_REQUEST, request);
//    }
//
//    @Override
//    protected ResponseEntity<Object> handleExceptionInternal(Exception ex, Object body, HttpHeaders headers,
//                                                             HttpStatus status, WebRequest request) {
//
//        if (body == null) {
//            body = Problema.builder()
//                    .dataHora(LocalDateTime.now())
//                    .mensagem(status.getReasonPhrase())
//                    .build();
//        } else if (body instanceof String) {
//            body = Problema.builder()
//                    .dataHora(LocalDateTime.now())
//                    .mensagem((String) body)
//                    .build();
//        }
//
//        return super.handleExceptionInternal(ex, body, headers, status, request);
//    }
//
//}