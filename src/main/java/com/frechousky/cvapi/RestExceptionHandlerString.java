package com.frechousky.cvapi;

public class RestExceptionHandlerString {

    public static final String PARAMETER_TYPE_MISMATCH_ERROR =
            "A parameter type mismatch error occurred while handling request <httpMethod> <requestUrl>";
    public static final String PARAMETER_TYPE_MISMATCH_ERROR_EXPLICIT_MESSAGE =
            "Expected type: '<expectedType>', received value: '<receivedValue>'";
    public static final String VALIDATION_ERROR = "Validation error(s) occurred while handling request <httpMethod> <requestUrl>";

}
