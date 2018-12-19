package com.crud.tasks.mapper;

import com.crud.tasks.domain.*;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class TrelloMapperTestSuite {
    private TrelloMapper trelloMapper = new TrelloMapper();

    @Test
    public void testMapBoardsWithEmptyList(){
        List<TrelloBoardDto> trelloBoardDtos = new ArrayList<>();
        List<TrelloBoard> trelloBoards = trelloMapper.mapToBoards(trelloBoardDtos);

        assertTrue(trelloBoards.isEmpty());
    }

    @Test(expected = NullPointerException.class)
    public void testMapBoardsWithNull(){
        List<TrelloBoard> trelloBoards = null;
        List<TrelloBoardDto> trelloBoardDtos = trelloMapper.mapToBoardsDto(trelloBoards);
    }

    @Test
    public void testMapToBoards() {
        TrelloListDto trelloListDto = new TrelloListDto("123", "Test trello list", false);
        List<TrelloListDto> trelloListDtoList = new ArrayList<>();
        trelloListDtoList.add(trelloListDto);
        TrelloBoardDto trelloBoardDto = new TrelloBoardDto("321", "test", trelloListDtoList);
        List<TrelloBoardDto> trelloBoardDtoList = new ArrayList<>();
        trelloBoardDtoList.add(trelloBoardDto);

        List<TrelloBoard> trelloBoards = trelloMapper.mapToBoards(trelloBoardDtoList);
        assertEquals("test", trelloBoards.get(0).getName());
        assertEquals("123", trelloBoards.get(0).getLists().get(0).getId());
    }

    @Test
    public void testMapToBoardsDto() {
        TrelloList trelloList = new TrelloList("123", "Test trello list", false);
        TrelloList trelloList2 = new TrelloList("456", "Test trello list2", false);

        List<TrelloList> trelloLists = new ArrayList<>();
        trelloLists.add(trelloList);
        trelloLists.add(trelloList2);

        TrelloBoard trelloBoard = new TrelloBoard("321", "test", trelloLists);
        TrelloBoard trelloBoard2 = new TrelloBoard("654", "test2", trelloLists);

        List<TrelloBoard> trelloBoards = new ArrayList<>();
        trelloBoards.add(trelloBoard);
        trelloBoards.add(trelloBoard2);

        List<TrelloBoardDto> trelloBoardDtos = trelloMapper.mapToBoardsDto(trelloBoards);
        assertEquals(2, trelloBoardDtos.size());
        assertEquals(4, trelloBoardDtos.stream().mapToInt(b -> b.getLists().size()).sum());
    }

    @Test(expected = NullPointerException.class)
    public void testMapToCardWithNull(){
        TrelloCardDto trelloCardDto = null;
        TrelloCard trelloCard = trelloMapper.mapToCard(trelloCardDto);
    }

    @Test
    public void testMapToCardDto() {
        TrelloCard trelloCard = new TrelloCard("testCard", "testDescription", "top", "123");
        TrelloCardDto trelloCardDto = trelloMapper.mapToCardDto(trelloCard);

        assertEquals("testCard", trelloCardDto.getName());
    }

    @Test
    public void testMapToCard() {
        TrelloCardDto trelloCardDto = new TrelloCardDto("testing", "testing2", "top", "321");
        TrelloCard trelloCard = trelloMapper.mapToCard(trelloCardDto);

        assertEquals("321", trelloCard.getListId());
    }
}