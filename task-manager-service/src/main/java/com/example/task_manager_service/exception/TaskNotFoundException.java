package com.example.task_manager_service.exception;

public class TaskNotFoundException extends RuntimeException{
    public TaskNotFoundException(String message){
        super(message);  //calls the runtimeException class
    }
}
