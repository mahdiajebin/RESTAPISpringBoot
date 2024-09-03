package com.example.task_manager_service;

import com.example.task_manager_service.model.Task;
import com.example.task_manager_service.repository.TaskRepository;
import com.example.task_manager_service.service.TaskService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
public class TaskServiceTest {
    @Mock
    private TaskRepository taskRepository;

    @InjectMocks
    private TaskService taskService;

    @Autowired
    public TaskServiceTest(){
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testPerformTask(){
        //set up mock behavior
        Task task1 = new Task("Test task 1", "Test decription", false);
        Task task2 = new Task("Test task 2", "Test decription 2 ", true);
        List<Task> tasks  = Arrays.asList(task1,task2);
        when(taskRepository.findAll()).thenReturn(tasks);
        //perform the task
        taskService.performTask();

        //Verify Interaction and assertions
        verify(taskRepository, times(1)).findAll();
        verify(taskRepository, times(1)).save(task1);  //verify save is called
        assertTrue(task1.isCompleted());
        assertTrue(task2.isCompleted());


    }

}
