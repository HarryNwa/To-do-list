package org.harry.todolist.dto.respond;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ApiRespond<T> {

    private T data;
}
