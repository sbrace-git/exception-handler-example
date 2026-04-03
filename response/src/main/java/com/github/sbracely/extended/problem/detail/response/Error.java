package com.github.sbracely.extended.problem.detail.response;

import org.jspecify.annotations.Nullable;

import java.util.Objects;

/**
 * Represents a detailed error information in the extended problem detail response.
 * <p>
 * This class encapsulates individual error details including error type, field name,
 * and error message. It is used to provide field-level validation error information
 * in API responses.
 * </p>
 *
 * @see ExtendedProblemDetail
 * @since 0.0.1-SNAPSHOT
 */
public class Error {
    /**
     * The type of error (PARAMETER, COOKIE, or HEADER).
     */
    @Nullable
    private Type type;
    /**
     * The name of the field that caused the error.
     */
    @Nullable
    private String field;
    /**
     * The error message describing what went wrong.
     */
    @Nullable
    private String message;

    /**
     * Default constructor for creating an empty error.
     */
    public Error() {
    }

    /**
     * Constructs an error with only a message.
     *
     * @param message the error message
     */
    public Error(@Nullable String message) {
        this.message = message;
    }

    /**
     * Constructs an error with a field name and message.
     *
     * @param field   the field name that caused the error
     * @param message the error message
     */
    public Error(@Nullable String field, @Nullable String message) {
        this.field = field;
        this.message = message;
    }

    /**
     * Constructs a fully populated error with type, field, and message.
     *
     * @param type    the type of error
     * @param field   the field name that caused the error
     * @param message the error message
     */
    public Error(@Nullable Type type, @Nullable String field, @Nullable String message) {
        this.type = type;
        this.field = field;
        this.message = message;
    }

    /**
     * Gets the error type.
     *
     * @return the error type, or {@code null} if not set
     */
    public @Nullable Type getType() {
        return type;
    }

    /**
     * Sets the error type.
     *
     * @param type the error type to set
     */
    public void setType(@Nullable Type type) {
        this.type = type;
    }

    /**
     * Gets the field name.
     *
     * @return the field name, or {@code null} if not set
     */
    public @Nullable String getField() {
        return field;
    }

    /**
     * Sets the field name.
     *
     * @param field the field name to set
     */
    public void setField(@Nullable String field) {
        this.field = field;
    }

    /**
     * Gets the error message.
     *
     * @return the error message, or {@code null} if not set
     */
    public @Nullable String getMessage() {
        return message;
    }

    /**
     * Sets the error message.
     *
     * @param message the error message to set
     */
    public void setMessage(@Nullable String message) {
        this.message = message;
    }

    @Override
    public final boolean equals(@Nullable Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Error error = (Error) o;
        return type == error.type && Objects.equals(field, error.field) && Objects.equals(message, error.message);
    }

    @Override
    public int hashCode() {
        return Objects.hash(type, field, message);
    }

    @Override
    public String toString() {
        return "Error{" +
                "type=" + type +
                ", field='" + field + '\'' +
                ", message='" + message + '\'' +
                '}';
    }

    /**
     * Enumeration of error types indicating the source of the validation error.
     * <ul>
     *     <li>{@code PARAMETER} - Error from request parameter</li>
     *     <li>{@code COOKIE} - Error from cookie value</li>
     *     <li>{@code HEADER} - Error from request header</li>
     * </ul>
     */
    public enum Type {
        PARAMETER,
        COOKIE,
        HEADER,
    }
}
