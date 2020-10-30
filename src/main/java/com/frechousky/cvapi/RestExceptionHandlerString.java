package com.frechousky.cvapi;

public class RestExceptionHandlerString {

    public static final String MESSAGE_NOT_READABLE_EXCEPTION =
            "Reading error(s) occurred while handling request <httpMethod> <requestUrl>";
    public static final String PARAMETER_TYPE_MISMATCH_ERROR =
            "A parameter type mismatch error occurred while handling request <httpMethod> <requestUrl>";
    public static final String PARAMETER_TYPE_MISMATCH_ERROR_EXPLICIT_MESSAGE =
            "Expected type: '<expectedType>', received value: '<receivedValue>'";
    public static final String UNHANDLED_EXCEPTION_ERROR =
            "An exception occurred while handling request <httpMethod> <requestUrl>";
    public static final String VALIDATION_ERROR = "Validation error(s) occurred while handling request <httpMethod> <requestUrl>";
}
