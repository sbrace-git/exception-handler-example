package org.example.exceptionhandlerexample.response;

import lombok.Getter;
import org.springframework.http.ProblemDetail;

public class ProblemDetails extends ProblemDetail {

    @Getter
    private String className = "ProblemDetails";

    public ProblemDetails() {
    }

    public ProblemDetails(ProblemDetail problemDetail) {
        super(problemDetail);
    }

}
