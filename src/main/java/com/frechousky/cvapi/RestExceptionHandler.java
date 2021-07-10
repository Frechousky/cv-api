package com.frechousky.cvapi;

import java.util.ArrayList;
import java.util.List;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;

import org.springframework.core.convert.ConversionFailedException;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.transaction.TransactionSystemException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;
import org.stringtemplate.v4.ST;

@RestControllerAdvice
public class RestExceptionHandler {

    @ExceptionHandler
    public ResponseEntity<ApiError> handleException(Exception ex, WebRequest request) {
        ServletWebRequest r = (ServletWebRequest) request;
        String message = new ST(RestExceptionHandlerString.UNHANDLED_EXCEPTION_ERROR)
                .add("httpMethod", r.getHttpMethod().toString())
                .add("requestUrl", r.getRequest().getRequestURL())
                .render();

        ApiError apiError = ApiError.builder()
                .message(message)
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .build();

        return ResponseEntity.status(apiError.getStatus()).body(apiError);
    }

    @ExceptionHandler
    public ResponseEntity<ApiError> handleEntityNotFoundException(ResourceNotFoundException ex, WebRequest request) {
        return ResponseEntity.notFound().build();
    }

    @ExceptionHandler
    public ResponseEntity<ApiError> handleTransactionSystemException(TransactionSystemException ex, WebRequest request) {
        Throwable base = ex;
        Throwable cause = ex.getCause();

        while (base != cause && cause != null) {
            base = cause;
            cause = cause.getCause();
        }

        if (base instanceof ConstraintViolationException) {
            return handleConstraintViolationException((ConstraintViolationException) base, request);
        }

        throw new RuntimeException(ex);
    }

    @ExceptionHandler
    public ResponseEntity<ApiError> handleConstraintViolationException(ConstraintViolationException ex, WebRequest request) {
        ServletWebRequest r = (ServletWebRequest) request;
        String message = new ST(RestExceptionHandlerString.VALIDATION_ERROR)
                .add("httpMethod", r.getHttpMethod().toString())
                .add("requestUrl", r.getRequest().getRequestURL())
                .render();

        List<String> errors = new ArrayList<>();
        for (ConstraintViolation<?> cv : ex.getConstraintViolations()) {
            errors.add(cv.getMessage());
        }

        ApiError apiError = ApiError.builder()
                .errors(errors)
                .message(message)
                .status(HttpStatus.BAD_REQUEST)
                .build();

        return ResponseEntity.status(apiError.getStatus()).body(apiError);
    }

    @ExceptionHandler
    public ResponseEntity<ApiError> handleConversionFailedException(ConversionFailedException ex, WebRequest request) {
        ServletWebRequest r = (ServletWebRequest) request;
        String message = new ST(RestExceptionHandlerString.PARAMETER_TYPE_MISMATCH_ERROR)
                .add("httpMethod", r.getHttpMethod().toString())
                .add("requestUrl", r.getRequest().getRequestURL())
                .render();


        List<String> errors = new ArrayList<>();
        errors.add(new ST(RestExceptionHandlerString.PARAMETER_TYPE_MISMATCH_ERROR_EXPLICIT_MESSAGE)
                .add("expectedType", ex.getTargetType().getObjectType().getSimpleName())
                .add("receivedValue", ex.getValue())
                .render());

        ApiError apiError = ApiError.builder()
                .status(HttpStatus.BAD_REQUEST)
                .message(message)
                .errors(errors)
                .build();

        return ResponseEntity.status(apiError.getStatus()).body(apiError);
    }

    @ExceptionHandler
    public ResponseEntity<ApiError> handleHttpMessageNotReadableException(HttpMessageNotReadableException ex, WebRequest request) {
        ServletWebRequest r = (ServletWebRequest) request;
        String message = new ST(RestExceptionHandlerString.MESSAGE_NOT_READABLE_EXCEPTION)
                .add("httpMethod", r.getHttpMethod().toString())
                .add("requestUrl", r.getRequest().getRequestURL())
                .render();

        List<String> errors = new ArrayList<>();
        errors.add(ex.getMostSpecificCause().getMessage());

        ApiError apiError = ApiError.builder()
                .status(HttpStatus.BAD_REQUEST)
                .message(message)
                .errors(errors)
                .build();

        return ResponseEntity.status(apiError.getStatus()).body(apiError);
    }

}
