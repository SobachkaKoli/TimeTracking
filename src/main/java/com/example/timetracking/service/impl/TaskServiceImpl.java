package com.example.timetracking.service.impl;

import com.example.timetracking.model.Task;
import com.example.timetracking.model.dto.TaskDTO;
import com.example.timetracking.model.enums.Status;
import com.example.timetracking.repository.TaskRepository;
import com.example.timetracking.service.TaskService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

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

    /**
     * Creates a new task based on the provided TaskDTO object.
     *
     * @param taskDTO Data Transfer Object containing task details (name, description).
     * @return The created Task object.
     */
    @Override
    public Task createTask(TaskDTO taskDTO) {
            log.info("Created new task");
            return taskRepository.save(
                    Task.builder()
                            .taskName(taskDTO.taskName())
                            .description(taskDTO.description())
                            .status(Status.CREATED)
                            .build());

    }

    /**
     * Updates an existing task with the specified ID, using data from the provided TaskDTO object.
     *
     * @param id      The ID of the task to update.
     * @param taskDTO Data Transfer Object containing updated task details.
     * @return The updated Task object.
     */
    @Override
    public Task updateTask(Long id, TaskDTO taskDTO) {
        log.info("Updating task with id: {}", id);
        Task currentTask = findTask(id);

        if (taskDTO.taskName()!= null){
            currentTask.setTaskName(taskDTO.taskName());
        }
        if (taskDTO.description()!= null){
            currentTask.setDescription(taskDTO.description());
        }
        if (taskDTO.start()!= null){
            currentTask.setStart(taskDTO.start());
        }
        if (taskDTO.finish()!= null){
            currentTask.setFinish(taskDTO.finish());
        }
        return taskRepository.save(currentTask);
    }

    /**
     * Starts a task with the specified ID, setting its status to IN_PROGRESS and start time to the current time.
     *
     * @param id The ID of the task to start.
     */
    @Override
    public void startTask(Long id) {
            Task task = findTask(id);
            task.setStart(Instant.now());
            task.setStatus(Status.IN_PROGRESS);
            taskRepository.save(task);
            log.info("The time is now {}", dateFormat.format(new Date()));

    }

    /**
     * Stops a task with the specified ID, setting its status to CLOSE, finish time to the current time, and calculating its duration.
     *
     * @param id The ID of the task to stop.
     */
    @Override
    public void stopTask(Long id) {

            log.info("Stopping task with id: {}", id);
            Task task = findTask(id);
            task.setFinish(Instant.now());
            task.setStatus(Status.CLOSE);
            task.setDuration(Duration.between(task.getStart(), task.getFinish()));
            taskRepository.save(task);
            log.info("The time is now {}", dateFormat.format(new Date()));
    }

    /**
     * Automatically closes all tasks with the IN_PROGRESS status, scheduled to run daily at 23:59.
     */
    @Override
    @Scheduled(cron = "0 59 23 * * *")
    public void closeTasks() {
            log.info("Closing tasks in progress");
            taskRepository.findAllByStatus(Status.IN_PROGRESS)
                    .stream()
                    .peek(task -> task.setStatus(Status.CLOSE))
                    .forEach(taskRepository::save);
            log.info("The time is now {}", dateFormat.format(new Date()));
    }

    /**
     * Retrieves a task by its ID.
     *
     * @param id The ID of the task to find.
     * @return The task if found.
     */
    private Task findTask(Long id) {
        return taskRepository.findById(id).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Task by id " + id + " not found!"));
    }
}
