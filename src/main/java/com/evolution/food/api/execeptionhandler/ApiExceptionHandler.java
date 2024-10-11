package com.evolution.food.api.execeptionhandler;

import com.evolution.food.api.domain.exception.BusinessException;
import com.evolution.food.api.domain.exception.EntityInUseException;
import com.evolution.food.api.domain.exception.EntityNotFoundException;
import com.fasterxml.jackson.databind.JsonMappingException.Reference;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import com.fasterxml.jackson.databind.exc.PropertyBindingException;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.TypeMismatchException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import java.util.List;
import java.util.stream.Collectors;

@ControllerAdvice
public class ApiExceptionHandler extends ResponseEntityExceptionHandler {

    @Override
    protected ResponseEntity<Object> handleTypeMismatch(TypeMismatchException ex, HttpHeaders headers,
                                                        HttpStatusCode status, WebRequest request) {

        HttpStatus httpStatus = HttpStatus.valueOf(status.value());
        if (ex instanceof MethodArgumentTypeMismatchException) {
            return handleMethodArgumentTypeMismatch((MethodArgumentTypeMismatchException) ex, headers, httpStatus, request);
        }
        return super.handleTypeMismatch(ex, headers, status, request);
    }

    private ResponseEntity<Object> handleMethodArgumentTypeMismatch(MethodArgumentTypeMismatchException ex,
                                                                    HttpHeaders headers, HttpStatus status, WebRequest request) {
        ProblemType problemType = ProblemType.INVALID_PARAMETER;
        String detail = String.format(
                "O parâmetro de URL '%s' recebeu o valor '%s', "
                        + "que é um tipo inválido. Corrija e informe um valor compatível com o tipo %s.",
                ex.getName(), ex.getValue(), ex.getRequiredType().getSimpleName());
        Problem problem = createProblemBuilder(status, problemType, detail).build();
        return handleExceptionInternal(ex, problem, headers, status, request);
    }

    @Override
    protected ResponseEntity<Object> handleNoResourceFoundException(NoResourceFoundException ex, HttpHeaders headers,
                                                                    HttpStatusCode status, WebRequest request) {

        HttpStatus httpStatus = HttpStatus.valueOf(status.value());
        ProblemType problemType = ProblemType.RESOURCE_NOT_FOUND;
        String detail = String.format(
                "O recurso %s, que você tentou acessar, é inexistente.",
                ex.getResourcePath());
        Problem problem = createProblemBuilder(httpStatus, problemType, detail).build();
        return handleExceptionInternal(ex, problem, headers, status, request);
    }

    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(
            HttpMessageNotReadableException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {

        Throwable rootCause = ExceptionUtils.getRootCause(ex);

        if(rootCause instanceof InvalidFormatException) {
            return handleInvalidFormatException((InvalidFormatException) ex.getRootCause(), headers, status, request);
        } else if (rootCause instanceof PropertyBindingException) {
            return handlePropertyBindingException((PropertyBindingException) rootCause, headers, (HttpStatus) status, request);
        }

        HttpStatus httpStatus = HttpStatus.valueOf(status.value());
        ProblemType problemType = ProblemType.INCOMPATIBLE_MESSAGE;
        String detail = "O corpo da requisicao esta invalido, verifique erro de sintaxe.";

        Problem problem = createProblemBuilder(httpStatus, problemType, detail).build();
        return handleExceptionInternal(ex, problem, headers, httpStatus, request);
    }

    private ResponseEntity<Object> handlePropertyBindingException(PropertyBindingException ex,
                                                                  HttpHeaders headers, HttpStatus status, WebRequest request) {

        // Foi criado o método joinPath para reaproveitar em todos os metodos que precisam
        // concatenar os nomes das propriedades (separando por ".")
        String path = joinPath(ex.getPath());

        ProblemType problemType = ProblemType.INCOMPATIBLE_MESSAGE;
        String detail = String.format("A propriedade '%s' nao existe. "
                + "Corrija ou remova essa propriedade e tente novamente.", path);

        Problem problem = createProblemBuilder(status, problemType, detail).build();

        return handleExceptionInternal(ex, problem, headers, status, request);
    }

    private ResponseEntity<Object> handleInvalidFormatException(
            InvalidFormatException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {

        String path = joinPath(ex.getPath());

        ProblemType problemType = ProblemType.INCOMPATIBLE_MESSAGE;
        HttpStatus httpStatus = HttpStatus.valueOf(status.value());
        String detail = String.format("A propriedade '%s' recebeu o valor '%s', "
                        + "que é de um tipo inválido. Corrija e informe um valor compatível com o tipo %s.",
                path, ex.getValue(), ex.getTargetType().getSimpleName());

        Problem problem = createProblemBuilder(httpStatus, problemType, detail).build();

        return handleExceptionInternal(ex, problem, headers, status, request);
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<Object> handleEntityNotFoundException(
            EntityNotFoundException ex, WebRequest request) {

        HttpStatus status = HttpStatus.NOT_FOUND;
        ProblemType problemType = ProblemType.RESOURCE_NOT_FOUND;
        String detail = ex.getMessage();

        Problem problem = createProblemBuilder(status, problemType, detail).build();

//        Problem problem = Problem.builder()
//                .status(status.value())
//                .type("https://evolutionfood.com.br/entity-not-found")
//                .title("Entidade nao encontrada.")
//                .detail(ex.getMessage())
//                .build();

        return handleExceptionInternal(ex, problem, new HttpHeaders(),
                status, request);
    }

    @ExceptionHandler(EntityInUseException.class)
    public ResponseEntity<Object> handleEntityInUseException(
            EntityInUseException ex, WebRequest request) {

        HttpStatus status = HttpStatus.CONFLICT;
        ProblemType problemType = ProblemType.ENTITY_IN_USE;
        String detail = ex.getMessage();

        Problem problem = createProblemBuilder(status, problemType, detail).build();

        return handleExceptionInternal(ex, problem, new HttpHeaders(),
                status, request);
    }

    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<Object> handleBusinessException(BusinessException ex, WebRequest request) {

        HttpStatus status = HttpStatus.BAD_REQUEST;
        ProblemType problemType = ProblemType.BUSINESS_ERROR;
        String detail = ex.getMessage();

        Problem problem = createProblemBuilder(status, problemType, detail).build();

        return handleExceptionInternal(ex, problem, new HttpHeaders(),
                status, request);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleGeneralException(Exception ex, WebRequest request) {

        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
        ProblemType problemType = ProblemType.SYSTEM_ERROR;
        String detail = "Ocorreu um erro interno inesperado no sistema. "
                + "Tente novamente e se o problema persistir, entre em contato "
                + "com o administrador do sistema.";

        // Importante colocar o printStackTrace (pelo menos por enquanto, que não estamos
        // fazendo logging) para mostrar a stacktrace no console
        // Se não fizer isso, você não vai ver a stacktrace de exceptions que seriam importantes
        // para você durante, especialmente na fase de desenvolvimento
        ex.printStackTrace();

        Problem problem = createProblemBuilder(status, problemType, detail).build();

        return handleExceptionInternal(ex, problem, new HttpHeaders(),
                status, request);
    }

    @Override
    protected ResponseEntity<Object> handleHttpRequestMethodNotSupported(
            HttpRequestMethodNotSupportedException ex, HttpHeaders headers, HttpStatusCode statusCode, WebRequest request) {

        // Customizando a mensagem de metodo nao suportado
        String customMessage = "O metodo de solicitacao 'PATCH' nao e compativel.";

        Problem problem = Problem.builder()
                .status(statusCode.value())
                .title(customMessage)
                .build();

        return new ResponseEntity<>(problem, headers, HttpStatus.METHOD_NOT_ALLOWED);
    }

    @Override
    protected ResponseEntity<Object> handleHttpMediaTypeNotSupported(
            HttpMediaTypeNotSupportedException ex, HttpHeaders headers, HttpStatusCode statusCode, WebRequest request) {

        // Customizando a mensagem de midia nao suportada
        String customMessage = "Tipo de midia nao suportado.";

        Problem problem = Problem.builder()
                .status(statusCode.value())
                .title(customMessage)
                .build();

        return new ResponseEntity<>(problem, headers, HttpStatus.UNSUPPORTED_MEDIA_TYPE);
    }

    @Override
    protected ResponseEntity<Object> handleExceptionInternal(
            Exception ex, Object body, HttpHeaders headers, HttpStatusCode statusCode, WebRequest request) {

        if (body == null) {
            body = Problem.builder()
                    .status(statusCode.value())
                    .title(ex.getMessage())
                    .build();
        }

        return super.handleExceptionInternal(ex, body, headers, statusCode, request);
    }

    private Problem.ProblemBuilder createProblemBuilder(HttpStatus status,
                                                        ProblemType problemType, String detail) {

        return Problem.builder()
                .status(status.value())
                .type(problemType.getPath())
                .title(problemType.getTitle())
                .detail(detail);
    }

    private String joinPath(List<Reference> references) {
        return references.stream()
                .map(ref -> ref.getFieldName())
                .collect(Collectors.joining("."));
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