package org.harry.todolist.controller;

import org.harry.todolist.dto.CreateTaskRequest;
import org.harry.todolist.dto.UpdateTaskRequest;
import org.harry.todolist.model.Task;
import org.harry.todolist.repo.ToDoListRepo;
import org.harry.todolist.service.ToDoListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/")
public class TaskController {
    @Autowired
    private ToDoListService toDoListService;


    @PostMapping("/task")
    public Task createNewTask(@RequestBody CreateTaskRequest createTaskRequest){
        try {
            return toDoListService.createNewTask(createTaskRequest);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    @GetMapping("/find")
    public String findTaskById(@RequestBody String id){
        try {
            String id1 = String.valueOf(toDoListService.findTaskById(id));
            return "Your task id is " + id1;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    @GetMapping("/findAll")
    public List<Task> findAllCompletedTask(){
        try {
            return  toDoListService.findAllCompletedTask();

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    @PostMapping("/update")
    public String updateTask(@RequestBody UpdateTaskRequest updateTaskRequest){
        try {
            String update = String.valueOf(toDoListService.updateTask(updateTaskRequest));
            return "Your task has been updated to " + update;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    @GetMapping("/describe")
    public String findByDescription(@RequestBody String description){
        try {
           String describe = String.valueOf(toDoListService.findByDescription(description));
           return "Your task description is found to be " + describe;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }



}
