package com.github.sbracely.nested.problem.detail;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Configuration properties for nested ProblemDetail exception handling.
 */
@ConfigurationProperties(prefix = "nested.problem-detail")
public class NestedProblemDetailProperties {

    /**
     * Whether to enable nested ProblemDetail exception handling.
     */
    private boolean enabled = true;

    /**
     * Default error code prefix.
     */
    private String errorCodePrefix = "ERR";

    /**
     * Whether to include stack trace in error response (not recommended for production).
     */
    private boolean includeStackTrace = false;

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public String getErrorCodePrefix() {
        return errorCodePrefix;
    }

    public void setErrorCodePrefix(String errorCodePrefix) {
        this.errorCodePrefix = errorCodePrefix;
    }

    public boolean isIncludeStackTrace() {
        return includeStackTrace;
    }

    public void setIncludeStackTrace(boolean includeStackTrace) {
        this.includeStackTrace = includeStackTrace;
    }

}
