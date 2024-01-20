package org.harry.todolist.controller;

import org.harry.todolist.dto.request.CreateTaskRequest;
import org.harry.todolist.dto.request.UpdateTaskRequest;
import org.harry.todolist.data.model.Task;
import org.harry.todolist.dto.respond.ApiRespond;
import org.harry.todolist.service.ToDoListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@CrossOrigin(origins = "http://localhost:63342")
//@CrossOrigin("*")
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
    @GetMapping("/findByDescription")
    public ApiRespond<Object> findByDescription(String description){
        try {
            Task task = toDoListService.findByDescription(description);
            return new ApiRespond<>(task);

        }catch (Exception e) {
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

    @GetMapping("/getAllTask")
    public ApiRespond<Object> getAllTask(@RequestParam(defaultValue = "0") int page,
                                         @RequestParam(defaultValue = "10") int pageSize) {

    @GetMapping("/findAll")
    public Iterable<Task> findAllCompletedTask(){

        try {
            List<Task> tasks = toDoListService.getAllTasks(page, pageSize);
            return new ApiRespond<>(tasks);
        } catch (Exception e) {
            return new ApiRespond<>(e.getMessage());
        }
    }


    @GetMapping("/findAll")
    public ApiRespond<Task> findAllCompletedTask(){
        try {
            Task task = (Task) toDoListService.findAllCompletedTask();
            return new ApiRespond<>(task);

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    @PostMapping("/update")
    public ApiRespond<Object> updateTask(@RequestBody UpdateTaskRequest updateTaskRequest){
        try {
           Task updateTask = toDoListService.updateTask(updateTaskRequest);
            return new ApiRespond<>(updateTask);
        } catch (Exception e) {
            return new ApiRespond<>(e.getMessage());
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
    public ApiRespond<String> deleteTaskByDescription(String description){
        try{
          toDoListService.deleteByDescription(description);
          String task = "Task has been deleted successfully";
          return new ApiRespond<>(task);

        }catch (Exception e){
            return new ApiRespond<>(e.getMessage());
        }
    }



}

