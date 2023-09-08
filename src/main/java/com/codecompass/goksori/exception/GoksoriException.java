package com.codecompass.goksori.exception;

import jakarta.validation.constraints.NotNull;
import org.springframework.http.HttpStatus;

public class GoksoriException extends RuntimeException {
    public GoksoriException(@NotNull final HttpStatus httpStatus) {
        super(httpStatus.getReasonPhrase());
    }
}
