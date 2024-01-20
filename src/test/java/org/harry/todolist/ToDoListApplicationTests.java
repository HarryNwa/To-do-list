package org.harry.todolist;

import org.harry.todolist.dto.request.CreateTaskRequest;
import org.harry.todolist.dto.request.UpdateTaskRequest;
import org.harry.todolist.data.model.Task;
import org.harry.todolist.data.repo.ToDoListRepo;
import org.harry.todolist.service.ToDoListService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.awt.*;
import java.time.LocalDateTime;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.*;

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
		task.setDescription(createTaskRequest.getDescription());
		task.setId(createTaskRequest.getId());
		toDoListService.createNewTask(createTaskRequest);
		assertThat(toDoListRepo.count(), is(1L));


		createTaskRequest.setDescription("Lecture by 10am");
		createTaskRequest.setTaskDate(LocalDateTime.of(2023, 12, 5,
				8, 0, 0));
		task.setId(createTaskRequest.getId());
		toDoListService.createNewTask(createTaskRequest);
		assertThat(toDoListService.count(),is(2L) );
//		System.out.println("Task id"+ task1.getId());
//		assertNotNull(task1.getId());


	}
	@Test
	public void findById(){
		createTaskRequest.setDescription("Lecture by 8am");
		createTaskRequest.setTaskDate(LocalDateTime.of(2023, 12, 5,
				8, 0, 0));
		toDoListService.createNewTask(createTaskRequest);
//		Task task = toDoListService.createNewTask(createTaskRequest);
		assertNotNull(toDoListService.findTaskById(task.getId()).getId());
		createTaskRequest.setDescription("work out by 9am");
		createTaskRequest.setTaskDate(LocalDateTime.of(2023, 12, 5,
				8, 0, 0));
		toDoListService.createNewTask(createTaskRequest);
//		Task task1 = toDoListService.createNewTask(createTaskRequest);
//		assertNotNull(toDoListService.findTaskById(task1.getId()).getId());
		assertThat(toDoListService.count(),is(2L) );


	}

	@Test
	public void findByDescription(){
		createTaskRequest.setDescription("Lecture by 8am");
		createTaskRequest.setTaskDate(LocalDateTime.of(2023, 12, 5,
				8, 0, 0));
		toDoListService.createNewTask(createTaskRequest);
		assertEquals("Lecture by 8am",toDoListService.findByDescription("Lecture by 8am").getDescription());

		createTaskRequest.setDescription("workout by 10:30am");
		createTaskRequest.setTaskDate(LocalDateTime.of(2023, 12, 5,
				10, 30, 0));
		toDoListService.createNewTask(createTaskRequest);
		assertEquals("workout by 10:30am",toDoListService.findByDescription("workout by 10:30am").getDescription());

	}
	@Test
	public void updateTask(){
		createTaskRequest.setDescription("Lecture by 8am");
		createTaskRequest.setTaskDate(LocalDateTime.of(2023, 12, 5, 8, 0, 0));
		createTaskRequest.setId("1");
		task.setId(createTaskRequest.getId());
		toDoListService.createNewTask(createTaskRequest);
		assertEquals("Lecture by 8am",toDoListService.findByDescription("Lecture by 8am").getDescription());

		updateTaskRequest.setOldDescription("Lecture by 8am");
		updateTaskRequest.setNewDescription("Lecture by 10am");
		updateTaskRequest.setId("1");
		task.setId(updateTaskRequest.getId());
		toDoListService.updateTask(updateTaskRequest);
		assertEquals("Lecture by 10am",toDoListService.findByDescription("Lecture by 10am").getDescription());


	}

	@Test
	public void deleteTaskById(){
		createTaskRequest.setDescription("Lecture by 8am");

		createTaskRequest.setTaskDate(LocalDateTime.of(2023, 12, 5,
				8, 0, 0));
		toDoListService.createNewTask(createTaskRequest);
//		Task task = toDoListService.createNewTask(createTaskRequest);

		createTaskRequest.setTaskDate(LocalDateTime.now());
		Task task = toDoListService.createNewTask(createTaskRequest);

		assertThat(toDoListRepo.count(), is(1L));

		createTaskRequest.setDescription("Lecture by 10am");

		createTaskRequest.setTaskDate (LocalDateTime.of(2023, 12, 5,
				8, 0, 0));
		toDoListService.createNewTask(createTaskRequest);
//		Task task1 = toDoListService.createNewTask(createTaskRequest);

		createTaskRequest.setTaskDate (LocalDateTime.now());
		Task task1 = toDoListService.createNewTask(createTaskRequest);

		assertThat(toDoListService.count(),is(2L) );

		toDoListService.deleteTask(task.getId());
		assertThat(toDoListRepo.count(), is(1L));

//		toDoListService.deleteTask(task1.getId());


		toDoListService.deleteTask(task1.getId());

		assertThat(toDoListRepo.count(), is(0L));


	}

	@Test
	public void confirmIfCompleted(){
		createTaskRequest.setDescription("Lecture by 8am");
		createTaskRequest.setTaskDate(LocalDateTime.now());
		createTaskRequest.setCompletionDate(LocalDateTime.of(2023, 12, 5, 10, 37, 0))
		createTaskRequest.setTaskDate(LocalDateTime.of(2023, 12, 5,
				8, 15, 0));
		createTaskRequest.setCompletionDate(LocalDateTime.of(2023, 12, 5,
				10, 37, 0));
		task.setCompletedTask(String.valueOf(createTaskRequest.getCompletionDate()));
		toDoListService.createNewTask(createTaskRequest);
		assertEquals("Lecture by 8am",toDoListService.findByDescription("Lecture by 8am").getDescription());
		assertTrue(toDoListService.isTaskComplete(createTaskRequest));
	}

	@Test
	public void findAllCompletedTask(){
		createTaskRequest.setDescription("Lecture by 8am");
		createTaskRequest.setTaskDate(LocalDateTime.of(2023, 12, 5, 8, 15, 0));
		createTaskRequest.setCompletionDate(LocalDateTime.of(2023, 12, 5, 9, 37, 0));
		createTaskRequest.setTaskDate(LocalDateTime.of(2023, 12, 5,
				8, 15, 0));
		createTaskRequest.setCompletionDate(LocalDateTime.of(2023, 12, 5,
				10, 37, 0));
		toDoListService.createNewTask(createTaskRequest);
//		Task task = toDoListService.createNewTask(createTaskRequest);
		assertEquals("Lecture by 8am",toDoListService.findByDescription("Lecture by 8am").getDescription());
		createTaskRequest.setDescription("Lecture by 10am");
		createTaskRequest.setTaskDate(LocalDateTime.of(2023, 12, 5,
				8, 15, 0));
		createTaskRequest.setCompletionDate(LocalDateTime.of(2023, 12, 5,
				10, 37, 0));
		createTaskRequest.setCompletedTask(String.valueOf(createTaskRequest.getCompletionDate()));

		toDoListService.createNewTask(createTaskRequest);
//		Task task2 = toDoListService.createNewTask(createTaskRequest);
		assertEquals("Lecture by 8am",toDoListService.findByDescription("Lecture by 8am").getDescription());
		toDoListService.findAllCompletedTask();

		Task task2 = toDoListService.createNewTask(createTaskRequest);
		assertNotNull(task2.getId());
		System.out.println(task2.getId());


		createTaskRequest.setDescription("Meeting by 2pm");
		createTaskRequest.setTaskDate(LocalDateTime.of(2023, 12, 5, 14, 0, 0));
		createTaskRequest.setCompletionDate(LocalDateTime.of(2023, 12, 5, 12, 30, 0));
		Task task1 = toDoListService.createNewTask(createTaskRequest);
		assertNotNull(task1.getId());
		System.out.println(task1.getId());

		createTaskRequest.setDescription("Shopping by 6pm");
		createTaskRequest.setTaskDate(LocalDateTime.of(2023, 12, 5, 18, 0, 0));
		createTaskRequest.setCompletionDate(LocalDateTime.of(2023, 12, 5, 11, 0, 0));
		Task task3 = toDoListService.createNewTask(createTaskRequest);
		assertNotNull(task3.getId());
		System.out.println(task3.getId());

		List<Task> completedTasks = toDoListService.findAllCompletedTask();
		assertThat(completedTasks.size(), is(3));
		assertTrue(completedTasks.stream().allMatch(task -> toDoListService.isTaskComplete(task.getDescription())));
	}

	}


