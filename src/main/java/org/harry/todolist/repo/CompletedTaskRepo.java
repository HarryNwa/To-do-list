package org.harry.todolist.repo;

import org.harry.todolist.model.Task;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface CompletedTaskRepo extends MongoRepository<Task, String> {

    Optional<Task> findByCompletedTask(String completedTask);
}
