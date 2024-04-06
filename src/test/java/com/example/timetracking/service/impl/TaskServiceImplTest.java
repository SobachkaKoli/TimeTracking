package com.example.timetracking.service.impl;

import com.example.timetracking.enums.Status;
import com.example.timetracking.exception.*;
import com.example.timetracking.model.Task;
import com.example.timetracking.dto.TaskDTO;
import com.example.timetracking.repository.TaskRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Instant;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class TaskServiceImplTest {

    Task expectedTaskCreate;
    Task existingTask;
    TaskDTO taskDTO;
    TaskDTO updateDTO;
    Long id = 1L;
    @Mock
    private TaskRepository taskRepository;
    @InjectMocks
    private TaskServiceImpl underTest;

    @BeforeEach
    void setUp() {
        id = 1L;

        taskDTO = new TaskDTO("Test Task", "Test Description", null, null);
        expectedTaskCreate = Task.builder()
                .id(id)
                .taskName("Test Task")
                .description("Test Description")
                .status(Status.CREATED)
                .start(null)
                .finish(null)
                .build();


        updateDTO = new TaskDTO("Updated Task Name", null, null, null);
        existingTask = Task.builder()
                .id(id)
                .taskName("Original Name")
                .description("Original Description")
                .status(Status.CREATED)
                .start(null)
                .finish(null)
                .build();

    }

    @Test
    public void createTask_givenValidDTO_whenSavingTask_thenTaskCreatedSuccessfully() {

        // Mock behavior
        when(taskRepository.save(any())).thenReturn(expectedTaskCreate);

        // Call the method
        Task actualTask = underTest.createTask(taskDTO);

        // Assertions
        assertEquals(taskDTO.taskName(), actualTask.getTaskName());
        assertEquals(taskDTO.description(), actualTask.getDescription());
        assertEquals(Status.CREATED, actualTask.getStatus());
    }

    @Test
    public void createTask_givenRepositoryError_whenSavingTask_thenCreateTaskExceptionThrown() {

        // Mock behavior (simulate repository error)
        when(taskRepository.save(any())).thenThrow(new RuntimeException());

        // Call the method and assert the exception
        assertThrows(CreateTaskException.class, () -> underTest.createTask(taskDTO));
    }

    @Test
    public void updateTask_givenExistingTaskAndValidDTO_whenUpdatingTask_thenTaskUpdatedSuccessfully() {

        // Mock behavior
        when(taskRepository.findById(id)).thenReturn(Optional.of(existingTask));
        when(taskRepository.save(existingTask)).thenReturn(existingTask);

        // Call the method
        Task updatedTask = underTest.updateTask(id, updateDTO);

        // Assertions
        assertEquals(updateDTO.taskName(), updatedTask.getTaskName());
        assertEquals("Original Description", updatedTask.getDescription());
        assertEquals(Status.CREATED, updatedTask.getStatus());
    }

    @Test
    public void updateTask_givenTaskNotFound_whenUpdatingTask_thenTaskNotFoundExceptionThrown() {

        // Mock behavior (task not found)
        when(taskRepository.findById(id)).thenReturn(Optional.empty());

        // Call the method and assert the exception
        assertThrows(TaskNotFoundException.class, () -> underTest.updateTask(id, updateDTO));
    }

    @Test
    public void updateTask_givenExpectedTask_thenThrowUpdateTaskException() {

        // Mock behavior
        when(taskRepository.findById(id)).thenReturn(Optional.of(expectedTaskCreate));
        when(taskRepository.save(expectedTaskCreate)).thenThrow(new RuntimeException("Mock exception"));

        // Call the method and expect an exception
        assertThrows(UpdateTaskException.class, () -> underTest.updateTask(id, updateDTO));

    }

    @Test
    public void startTask_givenExistingTask_whenStartingTask_thenTaskStartedSuccessfully() {

        // Mock behavior
        when(taskRepository.findById(id)).thenReturn(Optional.ofNullable(existingTask));
        when(taskRepository.save(existingTask)).thenReturn(existingTask);

        // Call the method
        underTest.startTask(id);

        // Assertions
        assertNotNull(existingTask.getStart());
        assertEquals(Status.IN_PROGRESS, existingTask.getStatus());
    }

    @Test
    public void givenTaskRepositoryError_whenStartTask_thenThrowStartStopException() {

        // Mock behavior
        when(taskRepository.findById(id)).thenReturn(Optional.of(existingTask));
        when(taskRepository.save(existingTask)).thenThrow(new RuntimeException("Mock exception"));

        // Act and Assert
        assertThrows(StartStopException.class, () -> underTest.startTask(id));

    }


    @Test
    public void stopTask_givenExistingInProgressTask_whenStoppingTask_thenTaskStoppedSuccessfully() {
        Long id = 1L;
        Task existingTask = Task.builder()
                .id(id)
                .taskName("Test Task")
                .description("Description")
                .status(Status.IN_PROGRESS)
                .start(Instant.now().minusSeconds(10))
                .build();

        // Mock behavior
        when(taskRepository.findById(id)).thenReturn(Optional.ofNullable(existingTask));
        when(taskRepository.save(existingTask)).thenReturn(existingTask);

        // Call the method
        underTest.stopTask(id);

        // Assertions
        assertNotNull(existingTask.getFinish());
        assertEquals(Status.CLOSE, existingTask.getStatus());
        assertTrue(existingTask.getDuration().getSeconds() >= 10);
    }

    @Test
    public void stopTask_givenTaskNotFound_whenStoppingTask_thenTaskNotFoundExceptionThrown() {
        //Prepare
        Long id = 3L;
        // Assertions
        assertThrows(TaskNotFoundException.class, () -> underTest.stopTask(id));
    }

    @Test
    public void whenStopTask_givenExistingTask_thenThrowStartStopException() {
        // Arrange
        Task existingTask = Task.builder()
                .id(id)
                .taskName("Test Task")
                .description("Description")
                .status(Status.IN_PROGRESS)
                .start(Instant.now().minusSeconds(10))
                .build();

        // Mock behavior
        when(taskRepository.findById(id)).thenReturn(Optional.of(existingTask));
        when(taskRepository.save(existingTask)).thenThrow(new RuntimeException("Mock exception"));

        // Act and Assert
        assertThrows(StartStopException.class, () -> underTest.stopTask(id));

    }

    @Test
    public void closeTasks_givenInProgressTasks_thenTasksAreClosed() {
        // Mock dependencies
        List<Task> inProgressTasks = Arrays.asList(
                Task.builder().id(1L).status(Status.IN_PROGRESS).build(),
                Task.builder().id(2L).status(Status.IN_PROGRESS).build()
        );
        when(taskRepository.findAllByStatus(Status.IN_PROGRESS)).thenReturn(inProgressTasks);
        // Call the method
        underTest.closeTasks();
        // Verify interactions
        verify(taskRepository).findAllByStatus(Status.IN_PROGRESS);
        for (Task task : inProgressTasks) {
            assertEquals(Status.CLOSE, task.getStatus());
            Mockito.verify(taskRepository).save(task);
        }
    }

    @Test
    public void givenExceptionWhenClosingTasks_thenThrowSchedulerException() {
        // Prepare mock behavior
        when(taskRepository.findAllByStatus(Status.IN_PROGRESS)).thenThrow(new RuntimeException("Mock exception"));

        // Call the method and expect an exception
        assertThrows(SchedulerException.class, () -> underTest.closeTasks());

    }


}
