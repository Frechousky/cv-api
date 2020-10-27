package com.frechousky.cvapi.rest;

import com.frechousky.cvapi.error.ApiError;
import com.frechousky.cvapi.rest.constants.CustomRestExceptionHandlerString;
import org.springframework.beans.TypeMismatchException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNullApi;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingPathVariableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import org.stringtemplate.v4.ST;

import java.util.ArrayList;
import java.util.List;

@ControllerAdvice
public class CustomRestExceptionHandler extends ResponseEntityExceptionHandler {

    @Override
    protected ResponseEntity<Object> handleHttpRequestMethodNotSupported(HttpRequestMethodNotSupportedException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        ServletWebRequest r = (ServletWebRequest) request;
        String message = new ST(CustomRestExceptionHandlerString.HTTP_METHOD_NOT_SUPPORTED_ERROR)
                .add("httpMethod", ex.getMethod())
                .add("requestUrl", r.getRequest().getRequestURL())
                .render();

        List<String> errors = new ArrayList<>();
        errors.add(new ST(CustomRestExceptionHandlerString.HTTP_METHOD_NOT_SUPPORTED_ERROR_EXPLICIT_MESSAGE)
                .add("supportedHttpMethods", ex.getSupportedHttpMethods().toString())
                .render());

        ApiError apiError = ApiError.builder()
                .status(HttpStatus.METHOD_NOT_ALLOWED)
                .message(message)
                .errors(errors)
                .build();

        return handleExceptionInternal(ex, apiError, headers, apiError.getStatus(), request);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        ServletWebRequest r = (ServletWebRequest) request;
        String message = new ST(CustomRestExceptionHandlerString.VALIDATION_ERROR)
                .add("httpMethod", r.getHttpMethod().toString())
                .add("requestUrl", r.getRequest().getRequestURL())
                .render();


        List<String> errors = new ArrayList<String>();
        for (FieldError error : ex.getBindingResult().getFieldErrors()) {
            errors.add(error.getField() + ": " + error.getDefaultMessage());
        }
        for (ObjectError error : ex.getBindingResult().getGlobalErrors()) {
            errors.add(error.getObjectName() + ": " + error.getDefaultMessage());
        }

        ApiError apiError = ApiError.builder()
                .status(HttpStatus.BAD_REQUEST)
                .message(message)
                .errors(errors)
                .build();

        return handleExceptionInternal(
                ex, apiError, headers, apiError.getStatus(), request);
    }

    @Override
    protected ResponseEntity<Object> handleMissingPathVariable(MissingPathVariableException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        ServletWebRequest r = (ServletWebRequest) request;
        String message = new ST(CustomRestExceptionHandlerString.MISSING_PATH_VARIABLE_ERROR)
                .add("httpMethod", r.getHttpMethod().toString())
                .add("requestUrl", r.getRequest().getRequestURL())
                .render();

        StringBuffer validUrlExample = r.getRequest().getRequestURL();
        if (!validUrlExample.toString().endsWith("/")) {
            validUrlExample.append("/");
        }
        validUrlExample.append("{" + ex.getVariableName() + "}").toString();
        List<String> errors = new ArrayList<String>();
        errors.add(new ST(CustomRestExceptionHandlerString.MISSING_PATH_VARIABLE_ERROR_EXPLICIT_MESSAGE)
                .add("variableName", ex.getVariableName())
                .add("variableType", ex.getParameter().getParameter().getType().getSimpleName())
                .add("validUrlExample", validUrlExample)
                .render());

        ApiError apiError = ApiError.builder()
                .status(HttpStatus.BAD_REQUEST)
                .message(message)
                .errors(errors)
                .build();

        return handleExceptionInternal(
                ex, apiError, headers, apiError.getStatus(), request);
    }

    @Override
    protected ResponseEntity<Object> handleTypeMismatch(TypeMismatchException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        ServletWebRequest r = (ServletWebRequest) request;
        String message = new ST(CustomRestExceptionHandlerString.PARAMETER_TYPE_MISMATCH_ERROR)
                .add("httpMethod", r.getHttpMethod().toString())
                .add("requestUrl", r.getRequest().getRequestURL())
                .render();

        List<String> errors = new ArrayList<String>();
        errors.add(new ST(CustomRestExceptionHandlerString.PARAMETER_TYPE_MISMATCH_ERROR_EXPLICIT_MESSAGE)
                .add("expectedType", ex.getRequiredType().getSimpleName())
                .add("receivedValue", ex.getValue())
                .render());

        ApiError apiError = ApiError.builder()
                .status(HttpStatus.BAD_REQUEST)
                .message(message)
                .errors(errors)
                .build();

        return handleExceptionInternal(
                ex, apiError, headers, apiError.getStatus(), request);
    }
}
