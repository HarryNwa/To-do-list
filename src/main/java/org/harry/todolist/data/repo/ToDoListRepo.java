package org.harry.todolist.data.repo;

import org.harry.todolist.data.model.Task;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface ToDoListRepo extends MongoRepository<Task, String> {
    

    Optional<Task> findTaskByDescription(String description);

    Optional<Task> findTaskById(String id);


}
