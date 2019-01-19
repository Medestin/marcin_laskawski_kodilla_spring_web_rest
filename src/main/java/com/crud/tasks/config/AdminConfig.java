package com.crud.tasks.config;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Getter
@Component
public class AdminConfig {
    @Value("${admin.mail}")
    private String adminMail;

    @Value("${admin.name}")
    private String adminName;

    @Value("${info.app.company.name}")
    private String companyName;

    @Value("${info.app.company.goal}")
    private String companyGoal;
}
