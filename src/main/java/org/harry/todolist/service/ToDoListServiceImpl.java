package org.harry.todolist.service;

import org.harry.todolist.dto.CreateTaskRequest;
import org.harry.todolist.model.Task;
import org.harry.todolist.repo.ToDoListRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

@Service
public class ToDoListServiceImpl implements ToDoListService {
    @Autowired
    private ToDoListRepo toDoListRepo;


    @Override
    public Task createNewTask(CreateTaskRequest createTaskRequest) {
        Task task = new Task();
        task.setDescription(createTaskRequest.getDescription());
        task.setTaskDate(createTaskRequest.getTaskDate());
        task.setId(createTaskRequest.getId());
        return toDoListRepo.save(task);
    }
    @Override
    public String getCurrentFormattedDateTime() {
        LocalDateTime currentDateTime = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return currentDateTime.format(formatter);
    }

    @Override
    public Task findTaskById(String id) {
        Optional<Task> task = toDoListRepo.findTaskById(id);
        if (task.isEmpty()) {
            throw new RuntimeException("Id not found");
        }
        if (task.get().getId().equals(id)) {
            return toDoListRepo.save(task.get());

        }
        return null;
    }

    @Override
    public List<Task> findAllCompletedTask() {
        return toDoListRepo.findAll();
    }

    @Override
    public void deleteTask(String id) {
        if (findTaskById(id).getId().equals(id)) {
            toDoListRepo.delete(findTaskById(id));
        }

    }

    @Override
    public Task updateTask(CreateTaskRequest createTaskRequest) {
        Task newTask = new Task();
        newTask.setDescription(createTaskRequest.getDescription());
        newTask.setTaskDate(createTaskRequest.getTaskDate());
        toDoListRepo.save(newTask);
        return newTask;
    }

    @Override
    public Task findByDescription(String description) {
        Optional<Task> task = toDoListRepo.findTaskByDescription(description);
        if (task.isEmpty()) {
            throw new RuntimeException("Task not found. Task either completed or never created");
        }
        if (task.get().getDescription().equals(description)) {
            return toDoListRepo.save(task.get());
        }
        return null;
    }
}
