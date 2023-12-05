// Function to create a new task
async function createNewTask(createTaskRequest) {
    try {
        const response = await fetch('https://localhost:8080/api/v1/task', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify(createTaskRequest),
        });
        const data = await response.json();
        return data;
    } catch (error) {
        console.error('Error creating new task:', error);
        return 'description or id already exist';
    }
}

// Function to find a task by ID
async function findTaskById(id) {
    try {
        const response = await fetch("https://8362-62-173-45-70.ngrok.io/api/v1/find?id=${id}");
        const data = await response.json();
        return data;
    } catch (error) {
        console.error('Error finding task by ID:', error);
        return error.message;
    }
}

// Function to find all completed tasks
async function findAllCompletedTasks() {
    try {
        const response = await fetch('https://8362-62-173-45-70.ngrok.io/api/v1/findAll');
        const data = await response.json();
        return data;
    } catch (error) {
        console.error('Error finding all completed tasks:', error);
        throw new Error('Failed to fetch completed tasks');
    }
}

// Function to update a task
async function updateTask(updateTaskRequest) {
    try {
        const response = await fetch('https://8362-62-173-45-70.ngrok.io/api/v1/update', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify(updateTaskRequest),
        });
        const data = await response.json();
        return data;
    } catch (error) {
        console.error('Error updating task:', error);
        throw new Error('Failed to update task');
    }
}

// Function to find a task by description
async function findByDescription(description) {
    try {
        const response = await fetch("https://8362-62-173-45-70.ngrok.io/api/v1/findByDescription?description=${description}");
        const data = await response.json();
        return data;
    } catch (error) {
        console.error('Error finding task by description:', error);
        throw new Error('Failed to fetch task by description');
    }
}

// Example usage:
// const newTask = createNewTask({ /* your createTaskRequest data */ });
// const taskById = findTaskById('your-task-id');
// const completedTasks = findAllCompletedTasks();
// const updatedTask = updateTask({ /* your updateTaskRequest data */ });
// const taskByDescription = findByDescription('your-description');
//
//
//
// const taskInput = document.getElementById("taskInput");
// const taskList = document.getElementById("taskList");
//
// function createNewTask() {
//     const taskText = taskInput.value.trim();
//
//     if (taskText !== "") {
//         const li = document.createElement("li");
//         const createdAt = new Date().toLocaleString();
//         li.innerHTML = `
//             <span>${taskText}</span>
//             <span class="createdAt">${createdAt}</span>
//             <button onclick="deleteTask(this)">Delete</button>
//         `;
//         taskList.appendChild(li);
//         taskInput.value = "";
//     }
// }
//
// function deleteTask(button) {
//     const li = button.parentNode;
//     taskList.removeChild(li);
// }
//
// //  code to fetch all tasks from Spring Boot backend
// fetch('https://8362-62-173-45-70.ngrok.io/api/createNewTask')
//     .then(response => response.json())
//     .then(tasks => {
//         console.log(tasks);
//     })
//     .catch(error => console.error('Error fetching tasks:', error));
//
// const newTask = {
//     text: 'Do something',
//     completed: false
// };
//
// fetch('http://localhost:8080/api/task', {
//     method: 'POST',
//     headers: {
//         'Content-Type': 'application/json'
//     },
//     body: JSON.stringify(newTask)
// })
//     .then(response => response.json())
//     .then(addedTask => {
//         console.log(addedTask);
//     })
//     .catch(error => console.error('Error adding task:', error));