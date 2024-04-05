package com.example.timetracking.controller;

import com.example.timetracking.models.Task;
import com.example.timetracking.records.TaskDTO;
import com.example.timetracking.service.TaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/tasks")
public class TaskController {

    private final TaskService taskService;

    @PostMapping
    public ResponseEntity<Task> createTask(@RequestBody TaskDTO taskDTO) {
        return ResponseEntity.ok(taskService.createTask(taskDTO));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Task> updateTask(@PathVariable Long id, @RequestBody TaskDTO taskDTO) {
        return ResponseEntity.ok(taskService.updateTask(id, taskDTO));
    }

    @PostMapping("/{id}/start")
    public ResponseEntity<Void> startTask(@PathVariable Long id) {
        taskService.startTask(id);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/{id}/stop")
    public ResponseEntity<Void> stopTask(@PathVariable Long id) {
        taskService.stopTask(id);
        return ResponseEntity.ok().build();
    }

}
