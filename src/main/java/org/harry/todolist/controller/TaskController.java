package org.harry.todolist.controller;

import org.harry.todolist.dto.request.CreateTaskRequest;
import org.harry.todolist.dto.request.UpdateTaskRequest;
import org.harry.todolist.data.model.Task;
import org.harry.todolist.dto.respond.ApiRespond;
import org.harry.todolist.service.ToDoListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin("*")
@RestController
@RequestMapping("/api/v1")
public class TaskController {
    @Autowired
    private ToDoListService toDoListService;



    @PostMapping("/task")
    public ApiRespond<Object> createNewTask(@RequestBody CreateTaskRequest createTaskRequest) {
        System.out.println("Received payload: " + createTaskRequest.toString());
        try {
            Task createdTask = toDoListService.createNewTask(createTaskRequest);
            return new ApiRespond<>(createdTask);
        } catch (Exception e) {
            return new ApiRespond<>(e.getMessage());
        }
    }
    @GetMapping("/find")
    public Object findTaskById(@RequestBody String id){
        try {
            return toDoListService.findTaskById(id);
        } catch (Exception e) {
            return e.getMessage();
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
    public Task updateTask(@RequestBody UpdateTaskRequest updateTaskRequest){
        try {
            return toDoListService.updateTask(updateTaskRequest);
        } catch (Exception e) {
            throw new NullPointerException(e.getMessage());
        }
    }

    @DeleteMapping("/delete")
    public String deleteTask(String id){
        try{
            toDoListService.deleteTask(id);
            return "deleted successfully";
        }catch (Exception e){
            return e.getMessage();
        }
    }
    @DeleteMapping("/deleteByDescription")
    public String deleteTaskByDescription(String description){
        try{
            toDoListService.deleteByDescription(description);
            return "deleted successfully";
        }catch (Exception e){
            return e.getMessage();
        }
    }



}

