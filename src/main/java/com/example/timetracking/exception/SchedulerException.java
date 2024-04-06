package com.example.timetracking.exception;

import org.springframework.http.HttpStatus;

public class SchedulerException extends CustomException {

    public static final int CODE = HttpStatus
            .BAD_GATEWAY.value();

    public SchedulerException(String message) {
        super(CODE, message);
    }
}
