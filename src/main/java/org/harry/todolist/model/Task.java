package org.harry.todolist.model;

import jakarta.annotation.Generated;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;
import java.time.LocalDateTime;
@Data
@Document()
@RequiredArgsConstructor
public class Task {
    @Id
    private String id;
    private LocalDateTime taskTime;
    private String description;
    private boolean completed;
    private LocalDateTime completionDateTime;



}
