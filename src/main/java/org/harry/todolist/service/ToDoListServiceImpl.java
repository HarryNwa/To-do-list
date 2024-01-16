package org.harry.todolist.service;

import org.harry.todolist.dto.request.CreateTaskRequest;
import org.harry.todolist.dto.request.UpdateTaskRequest;
import org.harry.todolist.data.model.Task;
import org.harry.todolist.data.repo.CompletedTaskRepo;
import org.harry.todolist.data.repo.ToDoListRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

import static org.harry.todolist.util.mapper.map;

@Service
public class ToDoListServiceImpl implements ToDoListService {
    @Autowired
    private ToDoListRepo toDoListRepo;
    @Autowired
    CompletedTaskRepo completedTaskRepo;


    @Override
    public Task createNewTask(CreateTaskRequest createTaskRequest)  {
        validate(createTaskRequest.getDescription());
        return toDoListRepo.save(map(createTaskRequest));
     




//        else{
//            throw new NullPointerException("description or id exist already");
//        }
//        validate(createTaskRequest.getDescription(),createTaskRequest.getId()) ;
//        task.setDescription(createTaskRequest.getDescription());
//        task.setId(createTaskRequest.getId());
//        task.setTaskTime(createTaskRequest.getTaskDate());
//        task.setCompletionDateTime(createTaskRequest.getCompletionDate());
//        getCurrentFormattedDateTime();
//
//        return toDoListRepo.save(task);

    }

    public void validate(String description) {
        if (toDoListRepo.findTaskByDescription(description).isPresent()){
            throw new RuntimeException("Task description exist already");

        }
//        for (Task task: toDoListRepo.findAll()){
//            if (task.getDescription().equalsIgnoreCase(description) || task.getId().equals(id)){
//                throw new RuntimeException("description or id exist already");
//            }
//        }
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
        List<Task> allTask = completedTaskRepo.findAll();
        if (allTask.isEmpty()){
            throw new RuntimeException("Task not found. Task either never created or not completed");
        }
////        if (allTask.get().getCompletedTask().equals(createTaskRequest.getCompletedTask())) {
//            return completedTaskRepo.save(allTask.get());
//        }
               return allTask;

    }

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

            return task.get();


    }

    @Override
    public boolean isTaskComplete(CreateTaskRequest createTaskRequest) {
        Task task = findByDescription(createTaskRequest.getDescription());
        LocalDateTime completed = LocalDateTime.parse(task.getCompletionDate());
        String completedTask = task.getCompletedTask();
        if (task.getCompletionDate() != null && completedTask != null){
            if(completed.isBefore(LocalDateTime.now())){
                completedTaskRepo.save(task);
               return true;

            }
        }
        return false;
    }
}
