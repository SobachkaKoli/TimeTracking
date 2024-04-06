package com.example.timetracking.exception;

public record ApiError(String path, String message, int statusCode) {

}
