package com.crud.tasks.trello.validator;

import com.crud.tasks.domain.TrelloBoard;
import com.crud.tasks.domain.TrelloCard;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class TrelloValidatorTestSuite {
    private TrelloValidator trelloValidator = new TrelloValidator();

    @Test
    public void testValidateProperCards(){
        TrelloCard trelloCard = new TrelloCard("test", "test description", "top", "1");
        TrelloCard trelloCard2 = new TrelloCard("not a drill", "description", "top", "1");

        trelloValidator.validateCard(trelloCard);
        trelloValidator.validateCard(trelloCard2);
    }

    @Test(expected = NullPointerException.class)
    public void testValidateNullCard(){
        TrelloCard trelloCard = null;
        trelloValidator.validateCard(trelloCard);
    }

    @Test
    public void testValidateProperBoardList(){
        TrelloBoard properTrelloBoard = new TrelloBoard("1", "proper name", new ArrayList<>());
        TrelloBoard testTrelloBoard = new TrelloBoard("1", "test", new ArrayList<>());

        List<TrelloBoard> unfilteredBoards = new ArrayList<>();
        unfilteredBoards.add(properTrelloBoard);
        unfilteredBoards.add(testTrelloBoard);

        List<TrelloBoard> validatedBoards = trelloValidator.validateBoards(unfilteredBoards);

        assertEquals(1, validatedBoards.size());
        assertEquals("proper name", validatedBoards.get(0).getName());
    }

    @Test
    public void testValidateEmptyBoardList(){
        List<TrelloBoard> unfilteredBoards = new ArrayList<>();

        List<TrelloBoard> validatedBoards = trelloValidator.validateBoards(unfilteredBoards);

        assertEquals(0, validatedBoards.size());
    }

    @Test(expected = NullPointerException.class)
    public void testValidateNullBoardList(){
        List<TrelloBoard> nullBoards = null;

        List<TrelloBoard> validatedBoards = trelloValidator.validateBoards(nullBoards);
    }
}