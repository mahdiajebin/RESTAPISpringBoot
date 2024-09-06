package com.example.task_manager_service.service;

import com.example.task_manager_service.logger.AppLogger;
import org.springframework.stereotype.Service;

@Service
public class NotificationService {
    public void sendNotification(String recipient, String messege){
        AppLogger.info("sending notification to " + recipient);
    }
}
