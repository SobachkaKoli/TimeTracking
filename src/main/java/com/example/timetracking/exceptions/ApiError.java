package com.example.timetracking.exceptions;

public record ApiError(String path, String message, int statusCode) {

}
