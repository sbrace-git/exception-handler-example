package org.example.exceptionhandlerexample.response;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.ProblemDetail;

import java.util.List;

@Getter
@Setter
public class ProblemDetails extends ProblemDetail {

    private String errorCode;

    private List<ParamError> errors;

    public ProblemDetails(ProblemDetail problemDetail) {
        super(problemDetail);
    }

}
