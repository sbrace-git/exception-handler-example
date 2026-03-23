package org.example.exceptionhandlerexample.response;

import lombok.Getter;
import lombok.Setter;
import org.jspecify.annotations.NonNull;
import org.springframework.http.ProblemDetail;

import java.util.List;

@Getter
@Setter
public class NestedProblemDetail extends ProblemDetail {

    private String errorCode;

    private List<Error> errors;

    public NestedProblemDetail() {
    }

    public NestedProblemDetail(ProblemDetail problemDetail) {
        super(problemDetail);
        this.errorCode = ErrorCode.httpStatusValue(problemDetail.getStatus());
    }

    @Override
    protected @NonNull String initToStringContent() {
        return super.initToStringContent() +
                ", errorCode='" + errorCode + "'" +
                ", errors=" + errors;
    }
}
