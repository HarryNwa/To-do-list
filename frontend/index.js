function createTodoList() {
    hideError();
    const inputElement = document.getElementById('input');
    const inputStartDateTime = document.getElementById('inputT');
    const inputEndDateTime = document.getElementById('inputE');

    const input = inputElement.value;
    const startTaskDateTime = inputStartDateTime.value;
    const endTaskDateTime = inputEndDateTime.value;

    if (!input || !startTaskDateTime || !endTaskDateTime) {
        alert("Please enter valid task details, start date, and end date");
        return;
    }

    const startDate = new Date(startTaskDateTime);
    const endDate = new Date(endTaskDateTime);

    if (startDate >= endDate) {
        alert("Start date should be before the end date");
        return;
    }

    const data = {
        description: input,
        taskDate: startTaskDateTime,
        completionDate: endTaskDateTime,
    };

    const createUrl = "http://localhost:8080/api/v1/task";

    console.log("Request payload:", data);

    fetch(createUrl, {
        method: 'POST',
        headers: {
            "Content-Type": "application/json; charset=UTF-8"
        },
        body: JSON.stringify(data)
    })
        .then(response => {
            console.log("Response status:", response.status);

            if (!response.ok) {
                throw new Error(`HTTP error! Status: ${response.status}`);
            }
            return response.json();
        })
        .then(data => {
            console.log("Response from server:", data);
            if(typeof data.data === 'string'){
                displayError(data.data)
            }else {
                updateUIWithTask(data.data);
            }
        })
        .catch((error) => {
            displayError(error)
        });
}
function updateUIWithTask(data) {
    const { id, description, completionDate, taskTime } = data;

    const todoTableBody = document.getElementById('todoListBody');
    const row = todoTableBody.insertRow();
    const cell1 = row.insertCell(0);
    const cell2 = row.insertCell(1);
    const cell3 = row.insertCell(2);

    cell1.textContent = description;
    cell2.textContent = taskTime;
    cell3.textContent = completionDate;

    cell1.style.paddingRight = '20px';
    cell2.style.paddingRight = '10px';

    console.log('You have successfully added a new task');

    clearInputFields();
}


function clearInputFields() {
    document.getElementById('input').value = '';
    document.getElementById('inputT').value = '';
    document.getElementById('inputE').value = '';

    hideError();
}

function searchForTask() {
    hideError();
    const resultContainer = document.querySelector(".resultContainer");
    resultContainer.innerHTML = "";

    const taskInput = document.querySelector(".taskInput");
    const searchDescription = taskInput.value.trim();

    if (!searchDescription) {
        alert("Please enter a description to search");
        return;
    }

    const createUrl = `http://localhost:8080/api/v1/findByDescription?description=${searchDescription}`;

    fetch(createUrl)
        .then(response => {
            if (!response.ok) {
                throw new Error(`HTTP error! Status: ${response.status}`);
            }
            return response.json();
        })
        .then(data => {
            console.log(data);
            if (typeof data.data === 'string') {
                displayError(data.data)
            } else {
                console.log(data.data)
                displayTaskResult(data.data);
            }
        })
        .catch(error);

    displayError(`Error fetching data: ${error.message}`);

}
function displayTaskResult(data) {
    const resultContainer = document.querySelector(".resultContainer");
    resultContainer.innerHTML = "";

    const table = document.createElement("table");

    const headerRow = table.insertRow();

    const header1 = document.createElement("th");
    header1.textContent = "Task description";
    headerRow.appendChild(header1);

    const header2 = document.createElement("th");
    header2.textContent = "Start date and time";
    headerRow.appendChild(header2);

    const header3 = document.createElement("th");
    header3.textContent = "End date and time";
    headerRow.appendChild(header3);

    const dataRow = table.insertRow();
    const cell1 = dataRow.insertCell(0);
    const cell2 = dataRow.insertCell(1);
    const cell3 = dataRow.insertCell(2);

    cell1.textContent = data.description;
    cell2.textContent = data.taskTime;
    cell3.textContent = data.completionDate;

    resultContainer.appendChild(table);
    resultContainer.style.display = "block";
}
function displayError(errorMessage) {
    const errorContainer = document.querySelector(".errorContainer");
    errorContainer.innerHTML = "";

    const errorElement = document.createElement("p");
    errorElement.textContent = errorMessage;

    errorContainer.appendChild(errorElement);
    errorContainer.style.display = "block";
}


function exit() {
    document.querySelector(".taskInput").value= " ";
}
function hideError() {
    const errorContainer = document.querySelector(".errorContainer");
    errorContainer.style.display = "none";
}
document.addEventListener('keypress', function (event) {
    if (event.key === 'Enter') {
        searchForTask();

    }
});
let currentPage = 0;
document.addEventListener('DOMContentLoaded', function () {
    fetchTasks();
});

function fetchTasks() {
    const taskContainer = document.getElementById('taskContainer');
    taskContainer.innerHTML = '';

    fetch(`http://localhost:8080/api/v1/getAllTask?page=${currentPage}`)
        .then(response => response.json())
        .then(data => {
            if (data && data.data && Array.isArray(data.data)) {
                data.data.forEach(task => {
                    const taskElement = createTaskElement(task);
                    taskContainer.appendChild(taskElement);
                });
            } else {
                console.error('Invalid response format:', data);
            }
        })
        .catch(error => console.error('Error:', error));
}

function fetchNextTasks() {
    if (currentPage >= 0) {
        currentPage++;
        fetchTasks();
    }
}
function fetchPrevTasks(){
    if (currentPage > 0) {
        currentPage--;
        fetchTasks();
    }

}


function createTaskElement(task) {
    const taskElement = document.createElement('div');
    taskElement.className = 'task';

    const descriptionElement = document.createElement('p');
    descriptionElement.textContent = task.description;

    const editButton = document.createElement('button');
    editButton.textContent = 'Edit';
    editButton.className = 'edit-btn';
    editButton.onclick = () => editTask(task);

    taskElement.appendChild(descriptionElement);
    taskElement.appendChild(editButton);

    return taskElement;
}
function editTask(task) {
    showEditForm(task);

}

function submitEdit(task, updatedDescription, updatedStartDate, updatedEndDate) {
    const updateTaskRequest = {
        oldDescription: task.description,
        newDescription: updatedDescription,
        taskDate: updatedStartDate,
        completedTask: updatedEndDate
    };

    fetch('http://localhost:8080/api/v1/update', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
        },
        body: JSON.stringify(updateTaskRequest),
    })
        .then(response => response.json())
        .then(data => {
            console.log(data);
            fetchTasks();
            closeOverlay();

        })
        .catch(error => console.error('Error:', error));
}


function showEditForm(task) {
    const overlay = document.createElement('div');
    overlay.className = 'overlay';

    const editForm = document.createElement('form');

    const descriptionLabel = document.createElement('label');
    descriptionLabel.textContent = 'Task:';
    const descriptionInput = document.createElement('input');
    descriptionInput.type = 'text';
    descriptionInput.value = task.description;
    descriptionLabel.appendChild(descriptionInput);

    const startDateLabel = document.createElement('label');
    startDateLabel.textContent = 'Start Date and Time:';
    const startDateInput = document.createElement('input');
    startDateInput.type = 'datetime-local';
    startDateInput.value = task.startDate;
    startDateLabel.appendChild(startDateInput);

    const endDateLabel = document.createElement('label');
    endDateLabel.textContent = 'End Date and Time:';
    const endDateInput = document.createElement('input');
    endDateInput.type = 'datetime-local';
    endDateInput.value = task.endDate;
    endDateLabel.appendChild(endDateInput);

    const submitButton = document.createElement('button');
    submitButton.textContent = 'Submit';
    submitButton.type = 'button';
    submitButton.onclick = () => submitEdit(task, descriptionInput.value, startDateInput.value, endDateInput.value);

    submitButton.style.marginRight = "20px"

    const cancelButton = document.createElement('button');
    cancelButton.textContent = 'Cancel';
    cancelButton.className = 'cancelButton';
    cancelButton.type = 'button';
    cancelButton.onclick = () => {
        closeOverlay()
        fetchTasks();

    };

    editForm.appendChild(descriptionLabel);
    editForm.appendChild(startDateLabel);
    editForm.appendChild(endDateLabel);
    editForm.appendChild(submitButton);
    editForm.appendChild(cancelButton);

    overlay.appendChild(editForm);

    document.body.appendChild(overlay);
}
function closeOverlay() {
    const overlay = document.querySelector('.overlay');
    if (overlay) {
        document.body.removeChild(overlay);
    }
}
function searchToEdit(){
    hideError();
    const resultContainer = document.querySelector(".resultContainer");
    resultContainer.innerHTML = "";

    const taskInput = document.querySelector(".taskInput");
    const searchDescription = taskInput.value.trim();

    if (!searchDescription) {
        alert("Please enter a description to search");
        return;
    }

    const createUrl = `http://localhost:8080/api/v1/findByDescription?description=${searchDescription}`;

    fetch(createUrl)
        .then(response => {
            if (!response.ok) {
                throw new Error(`HTTP error! Status: ${response.status}`);
            }
            return response.json();
        })
        .then(data => {
            console.log(data);
            if (typeof data.data === 'string') {
                displayError(data.data)
            } else {
                console.log(data.data)
                showEditForm(data.data);
            }
        })
        .catch(error);
        displayError(`Error fetching data: ${error.message}`);

}



