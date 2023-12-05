package org.harry.todolist.service;

import org.harry.todolist.dto.CreateTaskRequest;
import org.harry.todolist.dto.UpdateTaskRequest;
import org.harry.todolist.model.Task;
import org.harry.todolist.repo.CompletedTaskRepo;
import org.harry.todolist.repo.ToDoListRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class ToDoListServiceImpl implements ToDoListService {
    @Autowired
    private ToDoListRepo toDoListRepo;
    @Autowired
    CompletedTaskRepo completedTaskRepo;


    @Override
    public Task createNewTask(CreateTaskRequest createTaskRequest) {
        Task task = new Task();
        validate(createTaskRequest.getDescription(),createTaskRequest.getId()) ;
        task.setDescription(createTaskRequest.getDescription());
        task.setId(createTaskRequest.getId());
        task.setTaskTime(createTaskRequest.getTaskDate());
        task.setCompletionDateTime(createTaskRequest.getCompletionDate());
        getCurrentFormattedDateTime();

        return toDoListRepo.save(task);

    }

    public void validate(String description,String id){
        for (Task task: toDoListRepo.findAll()){
            if (task.getDescription().equalsIgnoreCase(description) || task.getId().equals(id)){
                throw new NullPointerException("description or id exist already");
            }
        }
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
            toDoListRepo.save(task.get());
            return task.get();

        }
      return null;
    }

    @Override
    public List<Task> findAllCompletedTask() {
        List <Task> allTask;
        allTask = completedTaskRepo.findAll();
//        List <Task> completedTask = new ArrayList<>();
//        for (Task task : allTask){
//            if (isTaskComplete(task.getDescription())){
               return allTask;

            }
//        }
//        if (!completedTask.isEmpty()) {
//            return completedTask;
//        } else {
//            throw new RuntimeException("No completed tasks found");
//        }
//    }

    @Override
    public long count() {
        return toDoListRepo.count();
    }

    @Override
    public void deleteTask(String id) {
        Task task = findTaskById(id);
        if (task.getId().equals(id)) {
            toDoListRepo.delete(task);
        }
    }

    @Override
    public void deleteByDescription(String description) {
        Task task = findByDescription(description);
        if (task.getDescription().equalsIgnoreCase(description))
            toDoListRepo.delete(task);
    }

    @Override
    public Task updateTask(UpdateTaskRequest updateTaskRequest) {
        Task task = (findTaskById(updateTaskRequest.getId()) != null) ? findTaskById(updateTaskRequest.getId()) : findByDescription(updateTaskRequest.getOldDescription());
        if (task.getDescription().equalsIgnoreCase(updateTaskRequest.getOldDescription())) {
            task.setDescription(updateTaskRequest.getNewDescription());
            task.setTaskTime(updateTaskRequest.getTaskDate());
            toDoListRepo.save(task);
            return task;
        }else{
            throw new NullPointerException("not found");
        }
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

    @Override
    public boolean isTaskComplete(String description) {
        Task task = findByDescription(description);
        LocalDateTime completed = task.getCompletionDateTime();
        if (task.getCompletionDateTime() != null){
            if(completed.isBefore(LocalDateTime.now())){
                completedTaskRepo.save(task);
               return true;

            }
        }
        return false;
    }
}
