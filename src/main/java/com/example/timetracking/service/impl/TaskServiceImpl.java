package com.example.timetracking.service.impl;

import com.example.timetracking.model.Task;
import com.example.timetracking.record.TaskRecord;
import com.example.timetracking.repository.TaskRepository;
import com.example.timetracking.service.TaskService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TaskServiceImpl implements TaskService {

    private final TaskRepository taskRepository;

    private final ModelMapper modelMapper;
    @Override
    public Task createTask(TaskRecord taskRecord) {
        return taskRepository.save(
                Task.builder()
                        .taskName(taskRecord.taskName())
                        .description(taskRecord.description())
                        .status(taskRecord.status())
                        .start(taskRecord.start())
                        .finish(taskRecord.finish())
                        .build());
    }

    @Override
    public Task updateTask(Long id, TaskRecord taskRecord) {
        Task currentTask = taskRepository.findById(id).orElseThrow();

        currentTask.setTaskName(taskRecord.taskName() != null ? taskRecord.taskName() : currentTask.getTaskName());
        currentTask.setDescription(taskRecord.description() != null ? taskRecord.description() : currentTask.getDescription());
        currentTask.setStatus(taskRecord.status() != null ? taskRecord.status() : currentTask.getStatus());
        currentTask.setStart(taskRecord.start() != null ? taskRecord.start() : currentTask.getStart());
        currentTask.setFinish(taskRecord.finish() != null ? taskRecord.finish() : currentTask.getFinish());

        return taskRepository.save(currentTask);
    }


}
