package org.harry.todolist.dto;

import lombok.Data;
import org.springframework.data.annotation.Id;

import java.time.LocalDateTime;
@Data
public class UpdateTaskRequest {
    @Id
    private String id;
    private String oldDescription;
    private String newDescription;
    private boolean completed;
}
