package com.example.timetracking.service;

import com.example.timetracking.model.Task;
import com.example.timetracking.record.TaskRecord;

public interface TaskService {
    Task createTask(TaskRecord taskRecord);

    Task updateTask(Long id, TaskRecord taskRecord);
}
