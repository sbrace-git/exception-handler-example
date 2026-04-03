package com.github.sbracely.extended.problem.detail.core.logging;

import org.apache.commons.logging.Log;
import org.jspecify.annotations.Nullable;
import org.slf4j.helpers.MessageFormatter;
import org.springframework.boot.logging.LogLevel;

/**
 * Utility class for logging at configurable log levels.
 * <p>
 * This class provides a convenient way to log messages at a dynamically
 * configured log level, supporting SLF4J-style placeholder syntax.
 * </p>
 *
 * @since 0.0.1-SNAPSHOT
 */
public final class ExtendedProblemDetailLog {

    private ExtendedProblemDetailLog() {
    }

    /**
     * Logs a message at the specified log level with placeholder support and optional exception.
     * <p>
     * Supports SLF4J-style placeholders: {@code logWithException(logger, DEBUG, "Error processing {}", ex, true, name)}
     * </p>
     *
     * @param logger          the logger to use
     * @param level           the log level
     * @param printStackTrace whether to include the exception stack trace in the log
     * @param ex              the exception to log
     * @param message         the message with optional placeholders
     * @param args            the arguments to replace placeholders
     */
    public static void log(Log logger, LogLevel level, boolean printStackTrace, @Nullable Throwable ex, String message, Object... args) {
        if (level == LogLevel.OFF) {
            return;
        }
        String formattedMessage = MessageFormatter.arrayFormat(message, args).getMessage();
        if (printStackTrace && ex != null) {
            level.log(logger, formattedMessage, ex);
        } else {
            level.log(logger, formattedMessage);
        }
    }
}
