# Extended Problem Detail

[![License](https://img.shields.io/badge/License-Apache%202.0-blue.svg)](https://www.apache.org/licenses/LICENSE-2.0)
[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-4.0.5-brightgreen.svg)](https://spring.io/projects/spring-boot)
[![Java](https://img.shields.io/badge/Java-17+-orange.svg)](https://openjdk.java.net/)

Enhanced Spring Boot ProblemDetail exception handling starter that provides out-of-the-box global exception handling for Spring MVC and WebFlux applications, with support for returning field-level detailed error information.

## Project Overview

This project extends Spring Framework's `ProblemDetail` (RFC 7807) to provide a richer error response format. When exceptions such as parameter validation failures occur, it can return specific field names and error messages, helping API consumers quickly identify and fix issues.

### Key Features

- **Auto-configuration** - Zero configuration startup, ready to use out of the box
- **Field-level errors** - Support for returning field errors from parameters, cookies, headers, and other sources
- **Dual framework support** - Supports both Spring WebMVC and Spring WebFlux
- **Standardized responses** - Follows RFC 7807 specification with custom extensions
- **High test coverage** - 50+ test methods covering 35+ exception types

## 📦 模块结构

```
extended-problem-detail
├── response                          # 核心响应类 (ExtendedProblemDetail, Error)
├── autoconfigure-webmvc              # WebMVC 自动配置与异常处理器
├── autoconfigure-webflux             # WebFlux 自动配置与异常处理器
├── starter-webmvc                    # WebMVC Starter (聚合依赖)
└── starter-webflux                   # WebFlux Starter (聚合依赖)
```

### 模块说明

| 模块 | 说明 |
|------|------|
| `response` | 核心响应类：`ExtendedProblemDetail` 和 `Error` |
| `autoconfigure-webmvc` | WebMVC 自动配置类和全局异常处理器 |
| `autoconfigure-webflux` | WebFlux 自动配置类和全局异常处理器 |
| `starter-webmvc` | WebMVC Starter，聚合所有必要依赖 |
| `starter-webflux` | WebFlux Starter，聚合所有必要依赖 |

## Quick Start

### Maven Dependencies

#### WebMVC Project

```xml
<dependency>
    <groupId>com.github.sbracely</groupId>
    <artifactId>extended-problem-detail-spring-boot-starter-webmvc</artifactId>
    <version>0.0.1-SNAPSHOT</version>
</dependency>
```

#### WebFlux Project

```xml
<dependency>
    <groupId>com.github.sbracely</groupId>
    <artifactId>extended-problem-detail-spring-boot-starter-webflux</artifactId>
    <version>0.0.1-SNAPSHOT</version>
</dependency>
```

### Configuration Options

Configure in `application.yml` (optional, enabled by default):

```yaml
extended:
  problem-detail:
    enabled: true  # Enable extended ProblemDetail, defaults to true
```

## Usage Examples

### Controller Example

```java
@RestController
@RequestMapping("/api")
public class UserController {
    
    @PostMapping("/users")
    public ResponseEntity<User> createUser(@Valid @RequestBody UserRequest request) {
        // Business logic
        return ResponseEntity.ok(user);
    }
    
    @GetMapping("/users/{id}")
    public ResponseEntity<User> getUser(@PathVariable Integer id) {
        // Business logic
        return ResponseEntity.ok(user);
    }
}
```

### Request Validation Example

```java
public class UserRequest {
    
    @NotBlank(message = "Username is required")
    private String username;
    
    @Email(message = "Invalid email format")
    private String email;
    
    @NotNull(message = "Age is required")
    @Min(value = 18, message = "Age must be at least 18")
    private Integer age;
    
    // getters and setters
}
```

### Error Response Example

When parameter validation fails, the returned JSON response:

```json
{
  "type": null,
  "title": "Bad Request",
  "status": 400,
  "detail": "Validation failed for argument [0] in public org.springframework.http.ResponseEntity<User> UserController.createUser(UserRequest)",
  "instance": "/api/users",
  "errors": [
    {
      "type": "PARAMETER",
      "field": "username",
      "message": "Username is required"
    },
    {
      "type": "PARAMETER",
      "field": "email",
      "message": "Invalid email format"
    },
    {
      "type": "PARAMETER",
      "field": "age",
      "message": "Age must be at least 18"
    }
  ]
}
```

## Supported Exception Types

This starter automatically handles the following exception types:

### WebMVC Support

- `MethodArgumentNotValidException` - `@Valid` annotation parameter validation failure
- `HandlerMethodValidationException` - Controller method parameter validation failure
- `WebExchangeBindException` - Data binding exception
- `HttpMediaTypeNotSupportedException` - Unsupported media type
- `HttpMediaTypeNotAcceptableException` - Not acceptable media type
- `MissingPathVariableException` - Missing path variable
- `MissingServletRequestParameterException` - Missing request parameter
- `MissingServletRequestPartException` - Missing request part
- `TypeMismatchException` - Type mismatch
- `HttpMessageNotReadableException` - Message not readable
- `HttpMessageNotWritableException` - Message not writable
- `HttpRequestMethodNotSupportedException` - Request method not supported
- `NoHandlerFoundException` - No handler found
- `AsyncRequestTimeoutException` - Async request timeout

### WebFlux Support

- `WebExchangeBindException` - Data binding exception
- `ServerWebInputException` - Server web input exception
- `ServerErrorException` - Server internal error
- `ResponseStatusException` - Response status exception
- And other WebFlux related exceptions

## Core Classes

### ExtendedProblemDetail

Extends Spring Framework's `ProblemDetail` and adds an `errors` property to store a list of detailed errors.

```java
public class ExtendedProblemDetail extends ProblemDetail {
    @Nullable
    private List<Error> errors;
    
    // getters and setters
}
```

### Error

Represents detailed information about a single error, including error type, field name, and error message.

```java
public class Error {
    @Nullable
    private Type type;      // PARAMETER, COOKIE, HEADER
    
    @Nullable
    private String field;   // Field name
    
    @Nullable
    private String message; // Error message
    
    // constructors, getters and setters
}
```

## Testing

The project includes a comprehensive test suite:

- **WebMVC Tests**: `MvcExtendedProblemDetailTests` - 200+ lines of test code
- **WebFlux Tests**: `FluxExtendedProblemDetailTests` - Complete reactive tests
- **Random Port Tests**: Integration tests with real environment support

Run tests:

```bash
# Run all tests
./mvnw test

# Run WebMVC tests
./mvnw test -pl autoconfigure-webmvc

# Run WebFlux tests
./mvnw test -pl autoconfigure-webflux
```

## Version Compatibility

| Component | Version Requirement |
|-----------|---------------------|
| Java      | 17+                 |
| Spring Boot | 4.0.5+            |
| Spring Framework | 6.x+         |

## Development Guide

### Custom Exception Handler

To customize exception handling logic, you can define your own bean in the application:

```java
@Configuration
public class CustomConfig {
    
    @Bean
    public MvcExtendedProblemDetailExceptionHandler customExceptionHandler() {
        return new CustomExceptionHandler();
    }
}
```

### Extend Response Format

You can add custom properties by extending `ExtendedProblemDetail`:

```java
public class CustomProblemDetail extends ExtendedProblemDetail {
    private String timestamp;
    private String errorCode;
    
    // getters and setters
}
```

## Contributing

Issues and Pull Requests are welcome!

## License

This project is licensed under the Apache License 2.0 - see the [LICENSE](LICENSE) file for details.

## Authors

- **sbrace** - [GitHub](https://github.com/sbracely)

## Related Links

- [Spring Boot Official Documentation](https://spring.io/projects/spring-boot)
- [RFC 9457 - Problem Details for HTTP APIs](https://www.rfc-editor.org/rfc/rfc9457)
- [Spring Framework ProblemDetail](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/http/ProblemDetail.html)
