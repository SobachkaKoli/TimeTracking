package com.example.timetracking.exception;

import lombok.Getter;

public abstract class CustomException extends RuntimeException {
    @Getter
    private final int code;

    CustomException(int code, String message) {
        super(message);
        this.code = code;
    }
}