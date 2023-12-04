package org.harry.todolist;

import org.harry.todolist.dto.CreateTaskRequest;
import org.harry.todolist.model.Task;
import org.harry.todolist.repo.ToDoListRepo;
import org.harry.todolist.service.ToDoListService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

@SpringBootTest
class ToDoListApplicationTests {
	@Autowired
	private ToDoListRepo toDoListRepo;
	@Autowired
	private ToDoListService toDoListService;

	CreateTaskRequest createTaskRequest = new CreateTaskRequest();
	@BeforeEach
	public void tearDown(){
		toDoListRepo.deleteAll();
	}

	@Test
	public void createTask() {
		Task task = new Task();
		createTaskRequest.setDescription("Lecture by 8am");
		createTaskRequest.setTaskDate(LocalDateTime.of(2023, 12, 5,
				8, 0, 0));

		task.setDescription(createTaskRequest.getDescription());
		task.setTaskDate(createTaskRequest.getTaskDate());
		task.setId(createTaskRequest.getId());
		toDoListService.createNewTask(createTaskRequest);

		assertThat(toDoListRepo.count(), is(1L));

	}

}
