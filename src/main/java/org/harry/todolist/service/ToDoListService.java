package org.harry.todolist.service;

import org.harry.todolist.dto.CompletedTaskRequest;
import org.harry.todolist.dto.CreateTaskRequest;
import org.harry.todolist.dto.UpdateTaskRequest;
import org.harry.todolist.model.Task;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface ToDoListService {
    Task createNewTask(CreateTaskRequest createTaskRequest);

    String getCurrentFormattedDateTime();
    Task findTaskById(String id);
//    List<Task> findAllCompletedTask();
    long count();

    void deleteTask(String id);

    List<Task> findAllCompletedTask();

    void deleteByDescription(String description);

    Task updateTask(UpdateTaskRequest updateTaskRequest);

//    Task findById(Long id);
    Task findByDescription(String description);
    boolean isTaskComplete(CreateTaskRequest createTaskRequest);
}
