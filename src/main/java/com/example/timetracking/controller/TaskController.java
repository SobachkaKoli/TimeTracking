package com.example.timetracking.controller;

import com.example.timetracking.model.Task;
import com.example.timetracking.model.dto.TaskDTO;
import com.example.timetracking.service.TaskService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/tasks")
@RequiredArgsConstructor
public class TaskController {

    private final TaskService taskService;

    @PostMapping
    @Operation(summary = "Creates a new task", description = "Returns the newly created task")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Task created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid task data"),})
    public ResponseEntity<Task> createTask(@RequestBody TaskDTO taskDTO) {
        return ResponseEntity.ok(taskService.createTask(taskDTO));
    }

    @PatchMapping("/{id}")
    @Operation(summary = "Updates an existing task", description = "Returns the newly created task")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Task updated successfully"),
            @ApiResponse(responseCode = "404", description = "Task not found"),
    })
    public ResponseEntity<Task> updateTask(@PathVariable Long id, @RequestBody TaskDTO taskDTO) {
        return ResponseEntity.ok(taskService.updateTask(id, taskDTO));
    }

    @Operation(summary = "Start task", description = "Start the task timer")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Task start successfully"),
            @ApiResponse(responseCode = "404", description = "Task not found"),
    })
    @PostMapping("/{id}/start")
    public ResponseEntity<Void> startTask(@PathVariable Long id) {
        taskService.startTask(id);
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "Stop task", description = "Stop the timer and show the duration")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Task start successfully"),
            @ApiResponse(responseCode = "404", description = "Task not found"),
    })
    @PostMapping("/{id}/stop")
    public ResponseEntity<Void> stopTask(@PathVariable Long id) {
        taskService.stopTask(id);
        return ResponseEntity.ok().build();
    }

}
