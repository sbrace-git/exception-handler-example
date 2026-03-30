# Nested Problem Detail

A Spring Boot Starter for comprehensive exception handling with nested ProblemDetail responses (RFC 9457).

## Features

- **Spring Boot Starter**: Auto-configuration for easy integration
- **Global exception handler**: Extends `ResponseEntityExceptionHandler`
- **Standardized error responses**: Using `ProblemDetail` (RFC 9457)
- **Nested error details**: Custom `NestedProblemDetail` with field-level error information
- **Configurable**: Enable/disable exception handling
- **Full test coverage**: 50+ test methods for 35+ exception classes

## Quick Start

### 1. Add Dependency

```xml
<dependency>
    <groupId>com.github.sbracely</groupId>
    <artifactId>nested-problem-detail-spring-boot-starter</artifactId>
    <version>0.0.1-SNAPSHOT</version>
</dependency>
```

### 2. Configure (Optional)

```yaml
nested:
  problem-detail:
    enabled: true              # Enable/disable exception handling
```

### 3. Done!

The starter auto-configures exception handling for your Spring MVC application.

## Exception Test Coverage

See [MVC Exception Test Mapping](nested-problem-detail-test-mvc/mvc-exception-test-mapping.md) for the complete test coverage table.

The project covers **50+ test methods** for **35+ exception classes** including:
- 20 base Spring MVC exceptions (HttpRequestMethodNotSupportedException, HttpMediaTypeNotSupportedException, etc.)
- 15 subclasses (MissingMatrixVariableException, MissingRequestCookieException, etc.)
- ResponseStatusException hierarchy (MethodNotAllowedException, NotAcceptableStatusException, etc.)

## Project Structure

```
.
├── nested-problem-detail-autoconfigure/     # Auto-configuration module
│   └── src/main/java/com/github/sbracely/nested/problem/detail/
│       ├── NestedProblemDetailAutoConfiguration.java
│       ├── NestedProblemDetailProperties.java
│       └── handler/RequestExceptionHandler.java
├── nested-problem-detail-spring-boot-starter/  # Starter module
├── nested-problem-detail-test-mvc/          # MVC test module
│   └── src/test/java/com/github/sbracely/nested/problem/detail/test/mvc/
├── nested-problem-detail-test-flux/         # WebFlux test module (TODO)
└── pom.xml
```

## Building from Source

```bash
./mvnw clean install
```

## Running Tests

```bash
# Run all tests
./mvnw test

# Run MVC tests only
./mvnw test -pl nested-problem-detail-test-mvc
```

## Requirements

- Java 17+
- Spring Boot 4.x

## License

Apache License 2.0
