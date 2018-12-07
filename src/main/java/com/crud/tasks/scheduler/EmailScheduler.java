package com.crud.tasks.scheduler;

import com.crud.tasks.config.AdminConfig;
import com.crud.tasks.domain.Mail;
import com.crud.tasks.repository.TaskRepository;
import com.crud.tasks.service.SimpleEmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class EmailScheduler {
    private SimpleEmailService simpleEmailService;
    private TaskRepository taskRepository;
    private AdminConfig adminConfig;

    private static final String SUBJECT = "Tasks: Daily reminder.";

    @Autowired
    public EmailScheduler(SimpleEmailService simpleEmailService, TaskRepository taskRepository, AdminConfig adminConfig) {
        this.simpleEmailService = simpleEmailService;
        this.taskRepository = taskRepository;
        this.adminConfig = adminConfig;
    }

    @Scheduled(cron = "0 0 10 * * *")
    public void sendInformation(){
        long size = taskRepository.count();
        simpleEmailService.send(new Mail(adminConfig.getAdminMail(), SUBJECT,
                "Currently in database you have " + size + " tasks."));
    }
}
