package org.example.exceptionhandlerexample.response;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.http.ProblemDetail;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class ProblemDetails extends ProblemDetail {

    private String errorCode;

    private List<ParamError> errors;

    public ProblemDetails(ProblemDetail problemDetail) {
        super(problemDetail);
        this.errorCode = ErrorCode.httpStatusValue(problemDetail.getStatus());
    }

}
