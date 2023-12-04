package org.harry.todolist.model;

import lombok.Data;
import org.springframework.data.annotation.Id;

import java.time.LocalDate;
import java.time.LocalDateTime;
@Data
public class Task {
    @Id
    private String id;
    private String task;
    private LocalDateTime taskDate;
    private boolean completed;

}
