package org.example.exceptionhandlerexample.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Error {
    private Type type;
    private String field;
    private String message;

    public Error(ObjectError objectError) {
        if (objectError instanceof FieldError fieldError) {
            this.field = fieldError.getField();
        }
        this.message = objectError.getDefaultMessage();
        this.type = Type.PARAMETER;
    }

    public enum Type {
        PARAMETER,
        COOKIE,
        HEADER,
    }
}