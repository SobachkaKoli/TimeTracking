package com.example.timetracking.model.dto;

import java.time.Instant;

public record TaskDTO(
        String taskName,
        String description,
        Instant start,
        Instant finish) {
}
