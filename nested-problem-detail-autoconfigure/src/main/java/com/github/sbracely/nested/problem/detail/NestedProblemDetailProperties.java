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

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

}
