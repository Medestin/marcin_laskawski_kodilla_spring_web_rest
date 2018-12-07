package com.crud.tasks.service;

import com.crud.tasks.config.AdminConfig;
import com.crud.tasks.domain.CreatedTrelloCard;
import com.crud.tasks.domain.Mail;
import com.crud.tasks.domain.TrelloBoardDto;
import com.crud.tasks.domain.TrelloCardDto;
import com.crud.tasks.trello.client.TrelloClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static java.util.Optional.ofNullable;

@Service
public class TrelloService {
    private TrelloClient trelloClient;
    private SimpleEmailService emailService;
    private AdminConfig adminConfig;

    private static final String SUBJECT = "Tasks: New Trello Card";

    @Autowired
    public TrelloService(TrelloClient trelloClient, SimpleEmailService emailService, AdminConfig adminConfig) {
        this.trelloClient = trelloClient;
        this.emailService = emailService;
        this.adminConfig = adminConfig;
    }

    public TrelloService() {
    }

    public List<TrelloBoardDto> fetchTrelloBoards(){
        return trelloClient.getTrelloBoards();
    }

    public CreatedTrelloCard createTrelloCard(final TrelloCardDto trelloCardDto){
        CreatedTrelloCard trelloCard = trelloClient.createNewCard(trelloCardDto);

        ofNullable(trelloCard).ifPresent(card -> emailService.send(new Mail(adminConfig.getAdminMail(), SUBJECT,
                "New card: " + card.getName() + " has been created on your Trello account."
        )));
        return trelloCard;
    }
}
