package com.example.timetracking.exceptions;

import org.springframework.http.HttpStatus;

public class TaskNotFoundException extends CustomException{

    public static final int CODE = HttpStatus
            .NOT_FOUND.value();
    public TaskNotFoundException(String msg){
        super(CODE,msg);
    }

}
