package org.example.exceptionhandlerexample.response;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.http.ProblemDetail;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class NestedProblemDetail extends ProblemDetail {

    private String errorCode;

    private List<Error> errors;

    public NestedProblemDetail(ProblemDetail problemDetail) {
        super(problemDetail);
        this.errorCode = ErrorCode.httpStatusValue(problemDetail.getStatus());
    }

}
