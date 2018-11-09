package com.crud.tasks.exception;

import static java.lang.String.format;

public class TaskNotFoundException extends Exception {

    public TaskNotFoundException(Long id){
        super(format("Task %s not found.", id));
    }
}
