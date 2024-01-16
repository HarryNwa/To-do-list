function createTodoList() {
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
            }
            updateUIWithTask(data.data);
        })
        .catch((error) => {
            displayError(error)
        });
}

function updateUIWithTask(data) {
    const { id, description, completionDate, taskTime } = data;

    const todoTableBody = document.getElementById('todoList');
    const row = todoTableBody.insertRow();
    const cell1 = row.insertCell(0);
    const cell2 = row.insertCell(1);
    const cell3 = row.insertCell(2);

    cell1.textContent = description;
    cell2.textContent = taskTime;
    cell3.textContent = completionDate;


    cell1.style.paddingRight = '20px';
    cell2.style.paddingRight = '10px';

    console.log("You have successfully added a new task");

    clearInputFields();
}


function clearInputFields() {
    document.getElementById('input').value = '';
    document.getElementById('inputT').value = '';
    document.getElementById('inputE').value = '';
}

function searchForTask() {
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
            displayTaskResult(data);
        })
        .catch(handleError);
}

function displayTaskResult(data) {
    const resultContainer = document.querySelector(".resultContainer");
    resultContainer.innerHTML = "";

    const descriptionElement = document.createElement("p");
    descriptionElement.textContent = data.description;
    resultContainer.appendChild(descriptionElement);

    const deleteButton = createButton("Delete", () => deleteTask(data.description));
    const exitButton = createButton("Exit", exit);

    resultContainer.appendChild(deleteButton);
    resultContainer.appendChild(exitButton);

    resultContainer.style.display = "block";
}

function createButton(text, onClick) {
    const button = document.createElement("button");
    button.textContent = text;
    button.onclick = onClick;
    return button;
}

function deleteTask(description) {
    const createUrl = `http://localhost:8080/api/v1/deleteByDescription?description=${description}`;

    fetch(createUrl, {
        method: 'DELETE',
        headers: {
            'Content-Type': 'application/json',
            'Accept': 'application/json',
        },
    })
        .then(response => {
            if (!response.ok) {
                throw new Error(`HTTP error! Status: ${response.status}`);
            }
            return response.json();
        })
        .then(data => {
            console.log('Response from server:', data);
            alert(`${description} was deleted successfully`);
            searchForTask();
        })
        .catch((error) => {
            console.log(error);
        });
}
function displayError(errorMessage) {
    const errorContainer = document.querySelector(".errorContainer");
    errorContainer.innerHTML = "";

    const errorElement = document.createElement("p");
    errorElement.textContent = errorMessage;

    errorContainer.appendChild(errorElement);
    errorContainer.style.display = "block";
}

function handleError(error) {
    console.error('Fetch error:', error);
    alert(`Error from the server: ${error.message}`);
}

function exit() {
    document.querySelector(".taskInput").value= " ";
}
