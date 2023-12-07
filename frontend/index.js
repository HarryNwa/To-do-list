function createTodoList() {
    const inputElement = document.getElementById('input');
    const input = inputElement.value


    if (!input) {
        alert("Please enter a valid task description");
        return;
    }

    const data = {
        description: input
    };

    const createUrl = "http://localhost:8080/api/v1/task";
    fetch(createUrl, {
        method: 'POST',
        headers: {
            "Content-Type": "application/json; charset:UTF-8"
        },
        body: JSON.stringify(data)
    })
        .then(response => {
            console.log("Response received");
            if (!response.ok) {
                throw new Error(`HTTP error! Status: ${response.status}`);
            }
            return response.json();
        })
        .then(data => {
            console.log("Data from server:", data);
            let {id, description, completedDateTime, taskTime} = data;
            console.log("Success!", data);
            console.log("You have successfully added a new task");
            inputElement.value = '';
        })
        .catch(error => {
                console.error("Fetch error:", error);
            });
}

