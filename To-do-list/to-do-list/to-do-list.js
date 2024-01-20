// const taskInput = document.getElementById("taskInput");
// const taskList = document.getElementById("taskList");
//
// function createTask() {
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
// fetch('http://localhost:8080/api/task')
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