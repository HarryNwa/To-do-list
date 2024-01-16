package org.harry.todolist.data.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Data
//@Document(collation = "Task")
public class Task {
    @Id
    private String id;
    private LocalDateTime taskTime;
    private String description;
    private LocalDateTime completionDate;
    private String completedTask;

    public String getCompletionDate() {
        if (completionDate != null) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMMM dd, yyyy 'Time' hh:mm a");
            return completionDate.format(formatter);
        }
        return null;


    }
    public String getTaskTime(){
        if(taskTime != null){
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMMM dd, yyyy 'Time' hh:mm a");
            return formatter.format(taskTime);

        }
        return null;
    }



}
