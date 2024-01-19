let currentPage = 0;
function hideError() {
    const errorContainer = document.querySelector(".errorContainer");
    errorContainer.style.display = "none";
}

function fetchGetAllTasks() {
    const taskContainer3 = document.getElementById('taskContainer3');
    taskContainer3.innerHTML = '';

    fetch(`http://localhost:8080/api/v1/getAllTask?page=${currentPage}`)
        .then(response => response.json())
        .then(data => {
            if (data && data.data && Array.isArray(data.data)) {
                data.data.forEach(task => {
                    const taskElement = createTaskElement(task);
                    taskContainer3.appendChild(taskElement);
                });
            } else {
                console.error('Invalid response format:', data);
            }
        })
        .catch(error => console.error('Error:', error));
}

function fetchNextTask() {
    if (currentPage >= 0) {
        currentPage++;
        fetchGetAllTasks();
    }
}

function fetchPrevTask() {
    if (currentPage > 0) {
        currentPage--;
        fetchGetAllTasks();
    }
}

function createTaskElement(task) {
    const taskElement = document.createElement('div');
    taskElement.className = 'task';

    const descriptionElement = document.createElement('p');
    descriptionElement.textContent = task.description;

    const deleteButton = document.createElement('button');
    deleteButton.textContent = 'Delete';
    deleteButton.className = 'delete-btn';
    deleteButton.onclick = () => deleteTaskConfirmation(task.description);

    taskElement.appendChild(descriptionElement);
    taskElement.appendChild(deleteButton);

    return taskElement;
}

function deleteTaskConfirmation(description) {
    const modal = document.getElementById('deleteConfirmationModal');
    const modalContent = modal.querySelector('.modal-content');
    modalContent.querySelector('p').textContent = `Are you sure you want to delete the task: ${description}?`;

    modal.style.display = 'flex';
    window.currentDeletingTaskDescription = description;
}

function confirmDelete() {
    hideError();
    const modal = document.getElementById('deleteConfirmationModal');
    modal.style.display = 'none';

    const taskDescription = window.currentDeletingTaskDescription;
    if (taskDescription) {
        const deleteUrl = `http://localhost:8080/api/v1/deleteByDescription?description=${taskDescription}`;

        fetch(deleteUrl, {
            method: 'DELETE',
        })
            .then(response => response.json())
            .then(data => {
                console.log(data);
                fetchGetAllTasks();
            })
            .catch(error => {
                console.error('Error:', error);
                displayError(`Error deleting task: ${error.message}`);
            });
    }
}

function cancelDelete() {
    hideError();
    const modal = document.getElementById('deleteConfirmationModal');
    modal.style.display = 'none';
}

document.addEventListener('DOMContentLoaded', function () {
    fetchGetAllTasks();
});
function searchToDelete(){
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
                deleteTaskConfirmation(data.data.description);

            }
        })
        .catch(error);
    displayError(`Error fetching data: ${error.message}`);

}

function displayError(errorMessage) {
    const errorContainer = document.querySelector(".errorContainer");
    errorContainer.innerHTML = "";

    const errorElement = document.createElement("p");
    errorElement.textContent = errorMessage;

    errorContainer.appendChild(errorElement);
    errorContainer.style.display = "block";
}
