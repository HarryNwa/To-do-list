package org.harry.todolist.dto.request;

import lombok.Data;
import org.springframework.data.annotation.Id;

import java.time.LocalDateTime;
@Data
public class UpdateTaskRequest {
    @Id
    private String id;
    private LocalDateTime taskDate;
    private String oldDescription;
    private String newDescription;
    private String completedTask;
    private LocalDateTime completionDate;
}
