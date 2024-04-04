package com.example.timetracking.record;

import java.util.Date;

public record TaskRecord(
        String taskName,
        String description,
        String status,
        Date start,
        Date finish) {
}
