package org.example.exceptionhandlerexample.response;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;

public class ErrorCode {
    public static final String PRE_FIX = "A00";
    public static final String UNKNOW = "000";

    public static String httpStatusCode(HttpStatusCode httpStatusCode) {
        if (null == httpStatusCode) {
            return PRE_FIX + UNKNOW;
        }
        return PRE_FIX + httpStatusCode.value();
    }

    public static String httpStatusValue(int httpStatusValue) {
        HttpStatus resolve = HttpStatus.resolve(httpStatusValue);
        return httpStatusCode(resolve);
    }
}
