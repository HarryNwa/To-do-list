package org.harry.todolist;

import org.harry.todolist.dto.CreateTaskRequest;
import org.harry.todolist.dto.UpdateTaskRequest;
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
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class ToDoListApplicationTests {
	@Autowired
	private ToDoListRepo toDoListRepo;
	@Autowired
	private ToDoListService toDoListService;

	CreateTaskRequest createTaskRequest = new CreateTaskRequest();
	UpdateTaskRequest updateTaskRequest = new UpdateTaskRequest();
	Task task;
	@BeforeEach
	public void tearDown(){
		toDoListRepo.deleteAll();
		task = new Task();
	}

	@Test
	public void createTask() {
		createTaskRequest.setDescription("Lecture by 8am");
		createTaskRequest.setTaskDate(LocalDateTime.of(2023, 12, 5,
				8, 0, 0));
		createTaskRequest.setId("1");

		toDoListService.createNewTask(createTaskRequest);
		assertThat(toDoListRepo.count(), is(1L));

		createTaskRequest.setDescription("Lecture by 10am");
		createTaskRequest.setTaskDate(LocalDateTime.of(2023, 12, 5,
				8, 0, 0));
		createTaskRequest.setId("2");
		toDoListService.createNewTask(createTaskRequest);
		assertThat(toDoListService.count(),is(2L) );


	}
	@Test
	public void findById(){
		createTaskRequest.setDescription("Lecture by 8am");
		createTaskRequest.setTaskDate(LocalDateTime.of(2023, 12, 5,
				8, 0, 0));
		createTaskRequest.setId("1");
		toDoListService.createNewTask(createTaskRequest);
		assertEquals("1",toDoListService.findTaskById("1").getId());
		createTaskRequest.setDescription("work out by 9am");
		createTaskRequest.setTaskDate(LocalDateTime.of(2023, 12, 5,
				8, 0, 0));
		createTaskRequest.setId("2");
		toDoListService.createNewTask(createTaskRequest);
		assertEquals("2",toDoListService.findTaskById("2").getId());
		assertThat(toDoListService.count(),is(2L) );


	}

	@Test
	public void findByDescription(){
		createTaskRequest.setDescription("Lecture by 8am");
		createTaskRequest.setTaskDate(LocalDateTime.of(2023, 12, 5,
				8, 0, 0));
		createTaskRequest.setId("1");
		toDoListService.createNewTask(createTaskRequest);
		assertEquals("Lecture by 8am",toDoListService.findByDescription("Lecture by 8am").getDescription());

		createTaskRequest.setDescription("workout by 10:30am");
		createTaskRequest.setTaskDate(LocalDateTime.of(2023, 12, 5,
				10, 30, 0));
		createTaskRequest.setId("2");
		toDoListService.createNewTask(createTaskRequest);
		assertEquals("workout by 10:30am",toDoListService.findByDescription("workout by 10:30am").getDescription());

	}
	@Test
	public void updateTask(){
		createTaskRequest.setDescription("Lecture by 8am");
		createTaskRequest.setTaskDate(LocalDateTime.of(2023, 12, 5,
				8, 0, 0));
		createTaskRequest.setId("1");
		toDoListService.createNewTask(createTaskRequest);
		assertEquals("Lecture by 8am",toDoListService.findByDescription("Lecture by 8am").getDescription());
		updateTaskRequest.setOldDescription("Lecture by 8am");
		updateTaskRequest.setNewDescription("Lecture by 10am");
		updateTaskRequest.setId("1");
		toDoListService.updateTask(updateTaskRequest);
		assertEquals("Lecture by 10am",toDoListService.findByDescription("Lecture by 10am").getDescription());


	}

	@Test
	public void updateTaskById(){
		createTaskRequest.setDescription("Lecture by 8am");
		createTaskRequest.setTaskDate(LocalDateTime.of(2023, 12, 5,
				8, 0, 0));
		createTaskRequest.setId("1");
		toDoListService.createNewTask(createTaskRequest);
		assertEquals("1",toDoListService.findTaskById("1").getId());
		createTaskRequest.setDescription("work out by 9am");
		createTaskRequest.setTaskDate(LocalDateTime.of(2023, 12, 5,
				8, 0, 0));
		createTaskRequest.setId("1");
		toDoListService.createNewTask(createTaskRequest);
		assertEquals("1",toDoListService.findTaskById("1").getId());
		assertThat(toDoListService.count(),is(1L) );

	}

}
