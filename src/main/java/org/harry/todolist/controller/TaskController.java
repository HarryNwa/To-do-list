package org.harry.todolist.controller;

import org.harry.todolist.dto.CreateTaskRequest;
import org.harry.todolist.dto.UpdateTaskRequest;
import org.harry.todolist.model.Task;
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
    public Object createNewTask(@RequestBody CreateTaskRequest createTaskRequest){
        try {
            return toDoListService.createNewTask(createTaskRequest) + toDoListService.getCurrentFormattedDateTime();
        } catch (Exception e) {
            return "description or id already exist";
        }
    }
    @GetMapping("/find")
    public Object findTaskById(@RequestBody String id){
        try {
//            String id1 = String.valueOf(toDoListService.findTaskById(id));
            return toDoListService.findTaskById(id);
        } catch (Exception e) {
//            return "not found";
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
//            String update = String.valueOf(toDoListService.updateTask(updateTaskRequest));
            return toDoListService.updateTask(updateTaskRequest);
        } catch (Exception e) {
            throw new NullPointerException(e.getMessage());
        }
    }
    @GetMapping("/findByDescription")
    public Task findByDescription(String description){
        try {
//           String describe = String.valueOf(toDoListService.findByDescription(description));
           return toDoListService.findByDescription(description);
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
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
