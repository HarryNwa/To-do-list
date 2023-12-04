package org.harry.todolist.controller;

import org.harry.todolist.dto.CreateTaskRequest;
import org.harry.todolist.service.ToDoListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/api/v1")
@RestController
public class TaskController {
    @Autowired
    private ToDoListService toDoListService;


    @PostMapping("/task")
    public void createNewTask(@RequestBody CreateTaskRequest createTaskRequest){
        try {
            toDoListService.createNewTask(createTaskRequest);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

}
