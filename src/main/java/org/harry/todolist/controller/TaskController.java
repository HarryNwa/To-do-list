package org.harry.todolist.controller;

import org.harry.todolist.dto.CreateTaskRequest;
import org.harry.todolist.model.Task;
import org.harry.todolist.repo.ToDoListRepo;
import org.harry.todolist.service.ToDoListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/api/v1")
@RestController
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


}
