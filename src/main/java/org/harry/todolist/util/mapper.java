package org.harry.todolist.util;

import org.harry.todolist.dto.request.CreateTaskRequest;
import org.harry.todolist.data.model.Task;

public class mapper {

    public static Task map( CreateTaskRequest createTaskRequest){
        Task task = new Task();
        System.out.println("Task Date: " + createTaskRequest.getTaskDate());

        task.setId(createTaskRequest.getId());
        task.setDescription(createTaskRequest.getDescription());
        task.setTaskTime(createTaskRequest.getTaskDate());
        task.setCompletionDate(createTaskRequest.getCompletionDate());
        task.setCompletedTask(createTaskRequest.getCompletedTask());

        return task;
    }

}
