package org.example.exceptionhandlerexample.response;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;

public class ErrorCode {
    public static final String BAD_REQUEST = "A00000";
    public static final String HTTP_METHOD_NOT_ALLOWED = "A00005";

    public static String httpStatusCode(HttpStatusCode httpStatusCode) {
        if (null == httpStatusCode) {
            return BAD_REQUEST;
        }
        if (httpStatusCode.isSameCodeAs(HttpStatus.METHOD_NOT_ALLOWED)) {
            return HTTP_METHOD_NOT_ALLOWED;
        }
        return BAD_REQUEST;
    }

    public static String httpStatusValue(int httpStatusValue) {
        HttpStatus resolve = HttpStatus.resolve(httpStatusValue);
        return httpStatusCode(resolve);
    }
}
