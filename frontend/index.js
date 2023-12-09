function createTodoList() {
    const inputElement = document.getElementById('input');
    const inputStartDateTime = document.getElementById('inputT');
    const inputEndDateTime = document.getElementById('inputE');

    const input = inputElement.value;
    const startDateTime = inputStartDateTime.value;
    const endDateTime = inputEndDateTime.value;

    if (!input ){
        alert("Please enter valid task details");
        return;
    }else if (!startDateTime){
        alert("Please enter a start date to start task");
        return;
    }else if (!endDateTime) {
        alert("Please enter a date to finish the task");
        return;

    }

    const data = {
        description: input,
        startDateTime: startDateTime,
        endDateTime: endDateTime
    };

    const createUrl = "http://localhost:8080/api/v1/task";

    fetch(createUrl, {
        method: 'POST',
        headers: {
            "Content-Type": "application/json; charset=UTF-8"
        },
        body: JSON.stringify(data)
    })
        .then(response => {
            if (!response.ok) {
                throw new Error(`HTTP error! Status: ${response.status}`);
            }
            return response.json();
        })
        .then(data => {
            let { id, description, completedDateTime, taskTime } = data;

            const listItem = document.createElement('li');
            listItem.textContent = `${description} - ${startDateTime} to ${endDateTime}`;

            const todoList = document.getElementById('todoList');
            todoList.appendChild(listItem);

            console.log("You have successfully added a new task");


            inputElement.value = '';
            inputStartDateTime.value = '';
            inputEndDateTime.value = '';
        })
        .catch(error => {
            console.error("Fetch error:", error);
        });
}
