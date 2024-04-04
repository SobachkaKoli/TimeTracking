package com.example.timetracking.controller;

import com.example.timetracking.model.Task;
import com.example.timetracking.record.TaskRecord;
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
    public ResponseEntity<Task> createTask(@RequestBody TaskRecord taskRecord) {
        return ResponseEntity.ok(taskService.createTask(taskRecord));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Task> editTask(@PathVariable Long id, @RequestBody TaskRecord taskRecord) {

        return ResponseEntity.ok(taskService.updateTask(id,taskRecord));
    }


}
