package com.example.timetracking.service.impl;

import com.example.timetracking.enums.Status;
import com.example.timetracking.exceptions.*;
import com.example.timetracking.models.Task;
import com.example.timetracking.records.TaskDTO;
import com.example.timetracking.repository.TaskRepository;
import com.example.timetracking.service.TaskService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.Instant;
import java.util.Date;

@Service
@RequiredArgsConstructor
@Slf4j
public class TaskServiceImpl implements TaskService {

    private final TaskRepository taskRepository;
    private final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");

    @Override
    @Transactional
    public Task createTask(TaskDTO taskDTO) {
        try {
            return taskRepository.save(
                    Task.builder()
                            .taskName(taskDTO.taskName())
                            .description(taskDTO.description())
                            .status(Status.CREATED)
                            .build());
        } catch (Exception e) {
            log.error("Error create task", e);
            throw new CreateTaskException("Error create task");

        }

    }

    @Override
    @Transactional
    public Task updateTask(Long id, TaskDTO taskDTO) {
        log.info("Updating task with id: {}", id);
        Task currentTask = taskRepository.findById(id).orElseThrow(
                () -> new TaskNotFoundException("Task by id " + id + " not found!"));

        currentTask.setTaskName(taskDTO.taskName() != null ? taskDTO.taskName() : currentTask.getTaskName());
        currentTask.setDescription(taskDTO.description() != null ? taskDTO.description() : currentTask.getDescription());
        currentTask.setStart(taskDTO.start() != null ? taskDTO.start() : currentTask.getStart());
        currentTask.setFinish(taskDTO.finish() != null ? taskDTO.finish() : currentTask.getFinish());
        try {
            return taskRepository.save(currentTask);
        } catch (Exception e) {
            log.error("Error updating task", e);
            throw new UpdateTaskException("Error updating task");
        }
    }

    @Override
    @Transactional
    public void startTask(Long id) {
        Task task = taskRepository.findById(id).orElseThrow(
                () -> new TaskNotFoundException("Task by id " + id + " not found!"));
        task.setStart(Instant.now());
        log.info("The time is now {}", dateFormat.format(new Date()));
        task.setStatus(Status.IN_PROGRESS);
        try {
            taskRepository.save(task);
        } catch (Exception e) {
            log.error("Error stopping task", e);
            throw new StartStopException("Unexpected error stopping task");
        }
    }

    @Override
    @Transactional
    public void stopTask(Long id) {
        log.info("Stopping task with id: {}", id);
        Task task = taskRepository.findById(id).orElseThrow(
                () -> new TaskNotFoundException("Task by id " + id + " not found!"));
        task.setFinish(Instant.now());
        task.setStatus(Status.CLOSE);
        task.setDuration(Duration.between(task.getStart(), task.getFinish()));
        try {
            taskRepository.save(task);
        } catch (Exception e) {
            log.error("Error stopping task", e);
            throw new StartStopException("Unexpected error stopping task");
        }
    }

    @Override
    @Transactional
    @Scheduled(cron = "0 59 23 * * *")
    public void closeTasks() {
        try {
            log.info("Closing tasks in progress");
            taskRepository.findAllByStatus(Status.IN_PROGRESS)
                    .stream()
                    .peek(task -> task.setStatus(Status.CLOSE))
                    .forEach(taskRepository::save);
        } catch (Exception e) {
            log.error("Failed to close tasks", e);
            throw new SchedulerException("Error losing tasks");
        }
    }


}
