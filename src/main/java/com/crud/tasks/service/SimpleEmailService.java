package com.crud.tasks.service;

import com.crud.tasks.domain.Mail;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class SimpleEmailService {
    private JavaMailSender javaMailSender;
    private MailCreatorService mailCreatorService;

    @Autowired
    public SimpleEmailService(JavaMailSender javaMailSender, MailCreatorService mailCreatorService) {
        this.javaMailSender = javaMailSender;
        this.mailCreatorService = mailCreatorService;
    }

    public SimpleEmailService() {
    }

    public void send(Mail mail){
        log.info("Preparing to send an email...");

        try {
            javaMailSender.send(createMimeMessage(mail));

            log.info("Message sent.");
        } catch (MailException e){
            log.error("Failed to send email.", e.getMessage(), e);
        }
    }

    public void sendDailyDatabaseCount(Mail mail){
        log.info("Preparing to send an email...");

        try {
            javaMailSender.send(createDailyMimeMessage(mail));

            log.info("Message sent.");
        } catch (MailException e){
            log.error("Failed to send email.", e.getMessage(), e);
        }
    }

    private MimeMessagePreparator createMimeMessage(final Mail mail){
        return mimeMessage -> {
            MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage);
            messageHelper.setTo(mail.getReceiverEmail());
            messageHelper.setSubject(mail.getSubject());
            messageHelper.setText(mailCreatorService.buildTrelloCardEmail(mail.getMessage()), true);
        };
    }

    private MimeMessagePreparator createDailyMimeMessage(final Mail mail){
        return mimeMessage -> {
            MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage);
            messageHelper.setTo(mail.getReceiverEmail());
            messageHelper.setSubject(mail.getSubject());
            messageHelper.setText(mailCreatorService.createDailyDatabaseMessage(mail.getMessage()), true);
        };
    }
}
