package com.github.sbracely.nested.problem.detail.response;

import org.jspecify.annotations.Nullable;
import org.springframework.http.ProblemDetail;

import java.util.ArrayList;
import java.util.List;

public class NestedProblemDetail extends ProblemDetail {

    @Nullable
    private List<Error> errors;

    public NestedProblemDetail() {
    }

    public NestedProblemDetail(ProblemDetail problemDetail) {
        super(problemDetail);
        if (problemDetail instanceof NestedProblemDetail nestedProblemDetail) {
            if (nestedProblemDetail.errors != null) {
                this.errors = new ArrayList<>(nestedProblemDetail.errors);
            }
        }
    }

    public @Nullable List<Error> getErrors() {
        return errors;
    }

    public void setErrors(@Nullable List<Error> errors) {
        this.errors = errors;
    }

    @Override
    protected String initToStringContent() {
        return super.initToStringContent() +
                ", errors=" + errors;
    }
}
