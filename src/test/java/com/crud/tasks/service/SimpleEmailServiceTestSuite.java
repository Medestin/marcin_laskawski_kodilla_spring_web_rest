package com.crud.tasks.service;

import com.crud.tasks.domain.Mail;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

import static org.junit.Assert.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@Slf4j
@RunWith(MockitoJUnitRunner.class)
public class SimpleEmailServiceTestSuite {
    @InjectMocks
    private SimpleEmailService simpleEmailService;

    @Mock
    private JavaMailSender javaMailSender;

    @Test
    public void shouldThrowException(){
        try{
        Mail mail = new Mail(null, "Test subject", "Test message");
        } catch (NullPointerException e){
            log.error(e.getMessage());
            log.info("Exception 1 thrown and caught.");
        }
        try{
            Mail mail2 = new Mail("test@test.com", null, "Test message");
        } catch (NullPointerException e){
            log.error(e.getMessage());
            log.info("Exception 2 thrown and caught.");
        }
        try{
            Mail mail3 = new Mail("test@test.com", "Test subject", null);
        } catch (NullPointerException e){
            log.error(e.getMessage());
            log.info("Exception 3 thrown and caught.");
        }
    }

    @Test
    public void shouldSendEmail(){
        Mail mail = new Mail("test@test.com", "Test subject", "Test message");
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(mail.getReceiverEmail());
        mailMessage.setSubject(mail.getSubject());
        mailMessage.setText(mail.getMessage());

        simpleEmailService.send(mail);

        verify(javaMailSender, times(1)).send(mailMessage);
    }

    @Test
    public void shouldSendEmailWithCCS(){
        Mail mail = new Mail("test@test.com", "Test subject", "Test message",
                "cc1@test.com", "cc2@test.com");

        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(mail.getReceiverEmail());
        mailMessage.setSubject(mail.getSubject());
        mailMessage.setText(mail.getMessage());
        mailMessage.setCc(mail.getCcs());

        simpleEmailService.send(mail);

        verify(javaMailSender, times(1)).send(mailMessage);
    }
}