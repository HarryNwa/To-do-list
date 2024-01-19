package org.harry.todolist.service;

import org.harry.todolist.dto.request.CreateTaskRequest;
import org.harry.todolist.dto.request.UpdateTaskRequest;
import org.harry.todolist.data.model.Task;

import java.util.List;

public interface ToDoListService {
   List <Task> getAllTasks(int page, int pageSize);
    Task createNewTask(CreateTaskRequest createTaskRequest);

    String getCurrentFormattedDateTime();
    Task findTaskById(String id);

    long count();

    void deleteTask(String id);

    List<Task> findAllCompletedTask();

    void deleteByDescription(String description);

    Task updateTask(UpdateTaskRequest updateTaskRequest);

//    Task findById(Long id);
    Task findByDescription(String description);
    boolean isTaskComplete(CreateTaskRequest createTaskRequest);
}
