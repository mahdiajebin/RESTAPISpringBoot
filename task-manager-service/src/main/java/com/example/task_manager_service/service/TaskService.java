package com.example.task_manager_service.service;


import com.example.task_manager_service.exception.TaskNotFoundException;
import com.example.task_manager_service.logger.AppLogger;
import com.example.task_manager_service.model.Task;
import com.example.task_manager_service.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

//BusinessLogic
@Service
public class TaskService {
    @Autowired
    private TaskRepository taskRepository;
    //this layer passes request to repository layer to save or get data from DB


    public Task createTask(Task task) {
        // You can add business logic here, like setting default values, validation, etc.
        validateTask(task);
        AppLogger.info("Creating new task: " + task.getTitle());
        return taskRepository.save(task);  // Save the task to the database
    }

    //Updating task
    public Task updateTask(Long id, Task taskDetails) {
    // Find the existing task
    Task task = findTaskById(id);
    updateTaskDetails(task, taskDetails); //helper method
    AppLogger.info("Updating task: " + task.getTitle());
    return taskRepository.save(task);

    }

    public List<Task> findAllTask(){
        return taskRepository.findAll();
    }

    public void deleteTask(Long id){
        Task task = findTaskById(id);
        taskRepository.delete(task);
    }

        //priority
    public List<Task> getHighPriorityTasks(){
        AppLogger.info("fething high priority tasks.");
        return taskRepository.findByPriorityLessThanEqual(2);
        //Spring Data JPA uses method names to create queries dynamically.
        // The method names follow specific conventions that Spring Data interprets to generate SQL queries.
        // that's how it knows what this function suppose to even though you didnt write any logic
    }
    //updating priority
    public void updateTaskPriority(long id, int newPriority){
        AppLogger.info("updating task priority");
        Task task = findTaskById(id);
        task.setPriority(newPriority);
        taskRepository.save(task);
    }

    public List<Task> getTaskDueToday(){
        AppLogger.info("fetching task due today");
        LocalDateTime today = LocalDateTime.now();
        LocalDateTime endOfDay = today.withHour(23).withMinute(59).withSecond(59);
        return taskRepository.findByDueDateBetween(today,endOfDay);
    }

    public void reScheduleTask(Long id, LocalDateTime newDueDate){
        AppLogger.info("Rescheduling task");
        Task task = findTaskById(id);
        task.setDueDate(newDueDate);
        taskRepository.save(task);
    }
    public List<Task>  getTaskByCategory(String category ){
        AppLogger.info("fetching task by Category");
        return taskRepository.findByCategory(category);
    }

    public void updateTaskCategory(Long id, String newCategory){
        AppLogger.info("updating task category");
        Task task = findTaskById(id);
        task.setCategory(newCategory);
        taskRepository.save(task);
    }

    //notification

    @Autowired
    private NotificationService notificationService; //injecting service

    public void notifyUpcomiingTask(){
        AppLogger.info("Notifying about upcoming task");
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime future = now.plusDays(1);// notify avbout task due within next 24 hours
        List<Task> tasks = taskRepository.findByDueDateBetween(now,future);
        for(Task task: tasks){
            notificationService.sendNotification("user@example.come", "Task due soon " + task.getTitle());
        }

    }


    //task analytics
    public long getTaskCompletedTaskCount(){
        AppLogger.info("Getting completed task count");
        return taskRepository.countByCompleted(true);
    }

    public long getTotalTaskCount(){
        AppLogger.info("getting task count");
        return taskRepository.count();
    }


    // Private helper methods
    private void validateTask(Task task) {
        if (task.getPriority() < 1 || task.getPriority() > 5) {
            throw new IllegalArgumentException("Priority must be between 1 and 5");
        }
        if (task.getDueDate() != null && task.getDueDate().isBefore(LocalDateTime.now())) {
            throw new IllegalArgumentException("Due date cannot be in the past");
        }
    }

    public Task findTaskById(Long id) {
        return taskRepository.findById(id).orElseThrow(() -> new TaskNotFoundException("Task with id " + id + " not found"));
    }

    private void updateTaskDetails(Task task, Task taskDetails) {
        task.setTitle(taskDetails.getTitle());
        task.setDescription(taskDetails.getDescription());
        task.setPriority(taskDetails.getPriority());
        task.setDueDate(taskDetails.getDueDate());
        task.setCompleted(taskDetails.isCompleted());
        task.setCategory(taskDetails.getCategory());
    }
}




