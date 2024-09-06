package com.example.task_manager_service.repository;

import com.example.task_manager_service.model.Task;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface TaskRepository extends JpaRepository<Task,Long> {
    List<Task> findByTitle(String title); // Example of safe query
    List<Task> findByPriorityLessThanEqual(int priority);
    List<Task>findByDueDateBetween(LocalDateTime start, LocalDateTime end);
    List<Task> findByCategory(String category);
    long countByCompleted(boolean completed);

}
