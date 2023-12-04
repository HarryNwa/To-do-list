package org.harry.todolist.dto;

import lombok.Data;
import org.springframework.data.annotation.Id;

import java.time.LocalDateTime;

@Data
public class CreateTaskRequest {
    @Id
    private String id;
    private String description;
    private boolean completed;
}
