package com.example.timetracking.records;

import java.time.Instant;

public record TaskDTO(
        String taskName,
        String description,
        Instant start,
        Instant finish) {
}
