package org.harry.todolist.repo;

import com.mongodb.client.MongoDatabase;
import org.harry.todolist.model.Task;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface ToDoListRepo extends MongoRepository<Task, String> {
    Optional<Task> findTaskById(String id);

    Optional<Task> findTaskByDescription(String description);
}
