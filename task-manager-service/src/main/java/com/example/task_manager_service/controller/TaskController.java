package com.example.task_manager_service.controller;


import ch.qos.logback.core.encoder.EchoEncoder;
import com.example.task_manager_service.exception.TaskNotFoundException;
import com.example.task_manager_service.model.Task;
import com.example.task_manager_service.repository.TaskRepository;
import com.example.task_manager_service.service.TaskService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

import static org.springframework.http.ResponseEntity.status;

@RestController
@RequestMapping("/api/tasks")
public class TaskController {

    @Autowired
    private TaskRepository taskRepository;
    @Autowired
    private TaskService taskService;
    //create task
    // you could just use Task clas but its not ideal for real life project so
    // Use ResponseEntity<Task> when you need more control over the response (like setting the status code or headers).
    //This method returns a ResponseEntity, which is a wrapper around the response body (Task in this case)
    @PostMapping("/create")
    public ResponseEntity<Task> createTask( @Valid @RequestBody Task task){
        try{
            Task createdTask = taskService.createTask(task);
            URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                    .path("/{id}")
                    .buildAndExpand(createdTask.getId())
                    .toUri();
            return ResponseEntity.created(location).body(createdTask);//201 created
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();// 500 Internal server error
        }
    }
    //update task
    @PutMapping
    public ResponseEntity<Task> updateTask(@PathVariable Long id, @Valid @RequestBody Task taskDetails) {

        Task updatedTask = taskService.updateTask(id,taskDetails);
        return ResponseEntity.ok(updatedTask);
    }


    @GetMapping
    public List<Task> getAllTask(){
        return taskService.findAllTask();
    }

    // Get Task by ID
    @GetMapping("/{id}")
    public ResponseEntity<Task> getTaskById(@PathVariable Long id){
        Task task = taskService.findTaskById(id);
        return ResponseEntity.ok(task);
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteTask(@PathVariable Long id) {
        taskService.deleteTask(id);  // Call the service to handle deletion
        return ResponseEntity.noContent().build();  // Return 204 No Content when deletion is successful
    }




}
