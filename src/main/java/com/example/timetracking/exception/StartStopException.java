package com.example.timetracking.exception;

import org.springframework.http.HttpStatus;

public class StartStopException extends CustomException {

    public static final int CODE = HttpStatus
            .INTERNAL_SERVER_ERROR.value();

    public StartStopException(String msg) {
        super(CODE, msg);
    }

}
