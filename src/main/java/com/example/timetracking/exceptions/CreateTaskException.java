package com.example.timetracking.exceptions;

import org.springframework.http.HttpStatus;

public class CreateTaskException extends CustomException{

    public static final int CODE = HttpStatus
            .BAD_REQUEST.value();
    public CreateTaskException(String message) {
        super(CODE, message);
    }
}
