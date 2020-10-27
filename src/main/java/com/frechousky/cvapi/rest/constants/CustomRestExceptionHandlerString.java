package com.frechousky.cvapi.rest.constants;

public class CustomRestExceptionHandlerString {

    public static final String HTTP_METHOD_NOT_SUPPORTED_ERROR = "HTTP method <httpMethod> not supported for this resource <requestUrl>";
    public static final String HTTP_METHOD_NOT_SUPPORTED_ERROR_EXPLICIT_MESSAGE =
            "Supported HTTP methods for this resource: <supportedHttpMethods>\n" +
                    "(supported HTTP methods might differ depending on path variables eg. /companies/1 might not return same supported HTTP methods as /companies)";
    public static final String MISSING_PATH_VARIABLE_ERROR = "Missing URL path variable for request <httpMethod> <requestUrl>";
    public static final String MISSING_PATH_VARIABLE_ERROR_EXPLICIT_MESSAGE =
            "Missing URL path variable <variableName> of type <variableType> (valid URL example: <validUrlExample>)";
    public static final String PARAMETER_TYPE_MISMATCH_ERROR =
            "A parameter type mismatch error occurred while handling request <httpMethod> <requestUrl>";
    public static final String PARAMETER_TYPE_MISMATCH_ERROR_EXPLICIT_MESSAGE =
            "Expected type: '<expectedType>', received value: '<receivedValue>'";
    public static final String VALIDATION_ERROR = "Validation error(s) occurred while handling request <httpMethod> <requestUrl>";

}
