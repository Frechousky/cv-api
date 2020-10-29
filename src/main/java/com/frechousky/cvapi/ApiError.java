package com.frechousky.cvapi;

import lombok.*;
import org.springframework.http.HttpStatus;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

@Data
@NoArgsConstructor
@Builder
@ToString
public class ApiError {

    private HttpStatus status;
    private Instant time = Instant.now();
    private String message;
    private List<String> errors;

    public ApiError(HttpStatus status, Instant time, String message, List<String> errors) {
        this.status = status;
        this.time = Optional.ofNullable(time).orElse(this.time); // see https://stackoverflow.com/a/50392165
        this.message = message;
        this.errors = errors;
    }
}
