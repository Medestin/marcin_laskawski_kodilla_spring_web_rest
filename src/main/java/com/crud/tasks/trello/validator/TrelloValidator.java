package com.crud.tasks.trello.validator;

import com.crud.tasks.domain.TrelloBoard;
import com.crud.tasks.domain.TrelloCard;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class TrelloValidator {
    private static final Logger LOGGER = LoggerFactory.getLogger(TrelloValidator.class);

    public void validateCard(final TrelloCard trelloCard){
        if(trelloCard.getName().toLowerCase().contains("test")){
            LOGGER.info("Seems someone is testing my application");
        } else {
            LOGGER.info("Seems my app is used in the proper way");
        }
    }

    public List<TrelloBoard> validateBoards(final List<TrelloBoard> trelloBoards){
        LOGGER.info("Begin filtering boards...");
        List<TrelloBoard> filteredTrelloBoards = trelloBoards.stream()
                .filter(trelloBoard -> !trelloBoard.getName().equalsIgnoreCase("test"))
                .collect(Collectors.toList());
        LOGGER.info("Boards filtered, current size: " + filteredTrelloBoards.size());

        return filteredTrelloBoards;
    }
}
