package com.example.task_manager_service.controller;


import com.example.task_manager_service.exception.TaskNotFoundException;
import com.example.task_manager_service.model.Task;
import com.example.task_manager_service.repository.TaskRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tasks")
public class TaskController {

    @Autowired
    private TaskRepository taskRepository;

    //create task
    @PostMapping
    public Task createTask( @Valid @RequestBody Task task){
        return taskRepository.save(task);
    }

    @GetMapping
    public List<Task> getAllTask(){
        return taskRepository.findAll();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteTask(@PathVariable Long id){
        return taskRepository.findById(id).map(task -> {
            taskRepository.delete(task);
            return ResponseEntity.ok().build();
        }).orElseThrow(()-> new TaskNotFoundException("Tasl with id" + id + " not found"));
    }

    // Get Task by ID
    public ResponseEntity<Task> getTaskById(@PathVariable Long id){
            Task task = taskRepository.findById(id).orElseThrow(() -> new TaskNotFoundException("Task with id " + id + "not found"));
            return ResponseEntity.ok(task);
    }

}
