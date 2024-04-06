package com.example.timetracking.exception;

import org.springframework.http.HttpStatus;

public class UpdateTaskException extends CustomException{

    public static final int CODE = HttpStatus
            .BAD_REQUEST.value();
    public UpdateTaskException( String message) {
        super(CODE, message);
    }
}
