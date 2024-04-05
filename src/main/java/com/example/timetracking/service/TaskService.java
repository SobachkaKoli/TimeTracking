package com.example.timetracking.service;

import com.example.timetracking.models.Task;
import com.example.timetracking.records.TaskDTO;

public interface TaskService {

    Task createTask(TaskDTO taskDTO);

    Task updateTask(Long id, TaskDTO taskDTO);

    void startTask(Long id);

    void stopTask(Long id);

    void closeTasks();
}
