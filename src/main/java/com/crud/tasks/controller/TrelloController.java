package com.crud.tasks.controller;

import com.crud.tasks.domain.CreatedTrelloCard;
import com.crud.tasks.domain.TrelloBoardDto;
import com.crud.tasks.domain.TrelloCardDto;
import com.crud.tasks.service.TrelloService;
import com.crud.tasks.trello.client.TrelloClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/v1/trello")
@CrossOrigin("*")
public class TrelloController {
    private TrelloService trelloService;

    @Autowired
    public TrelloController(TrelloService trelloService) {
        this.trelloService = trelloService;
    }

    @GetMapping(value = "boards")
    public List<TrelloBoardDto> getTrelloBoards(){
        return new ArrayList<>(trelloService.fetchTrelloBoards());
    }

    @GetMapping(value = "boards/")
    public List<TrelloBoardDto> getTrelloBoards(@Param("filter") String filter) {
        return trelloService.fetchTrelloBoards().stream()
                .filter(trelloBoardDto -> trelloBoardDto.getName().toLowerCase().contains(filter.toLowerCase()))
                .collect(Collectors.toList());
    }

    @PostMapping(value = "cards")
    public CreatedTrelloCard createNewCard(@RequestBody TrelloCardDto trelloCardDto){
        return trelloService.createTrelloCard(trelloCardDto);
    }
}
