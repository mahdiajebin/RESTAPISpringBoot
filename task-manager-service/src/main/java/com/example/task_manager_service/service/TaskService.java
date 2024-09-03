package com.example.task_manager_service.service;


import com.example.task_manager_service.logger.AppLogger;
import com.example.task_manager_service.model.Task;
import com.example.task_manager_service.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

//BusinessLogic
@Service
public class TaskService {
    @Autowired
    private TaskRepository taskRepository;

    public void performTask(){
        AppLogger.info("Starting task execution");
        try{
            //task execution logic
            List<Task> tasks = taskRepository.findAll(); //retrieves all task from the database
            for(Task task: tasks) {
                processTask(task);
            }
            AppLogger.info("Task execution completed successfully");
        }catch (Exception e){
            AppLogger.error("An error occured while executing the task", e);
        }
    }

    private void processTask(Task task){
        //Process each task
        if(task.isCompleted()){
            AppLogger.info("tasl is already completed" + task.getTitle());
        }else {
            //perfrom task processing
            task.setCompleted(true);
            taskRepository.save(task);//save to db
            AppLogger.info("Processed and completed task" + task.getTitle());
        }
    }



}


