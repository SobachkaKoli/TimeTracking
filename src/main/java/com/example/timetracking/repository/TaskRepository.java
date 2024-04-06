package com.example.timetracking.repository;

import com.example.timetracking.model.enums.Status;
import com.example.timetracking.model.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {
    List<Task> findAllByStatus(Status status);

}
