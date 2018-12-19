package com.crud.tasks.controller;

import com.crud.tasks.domain.CreatedTrelloCardDto;
import com.crud.tasks.domain.TrelloBoardDto;
import com.crud.tasks.domain.TrelloCardDto;
import com.crud.tasks.service.TrelloService;
import com.crud.tasks.trello.facade.TrelloFacade;
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
    private TrelloFacade trelloFacade;

    @Autowired
    public TrelloController(TrelloFacade trelloFacade) {
        this.trelloFacade = trelloFacade;
    }

    @GetMapping(value = "boards")
    public List<TrelloBoardDto> getTrelloBoards(){
        return new ArrayList<>(trelloFacade.fetchTrelloBoards());
    }

    @GetMapping(value = "boards/")
    public List<TrelloBoardDto> getTrelloBoards(@Param("filter") String filter) {
        return trelloFacade.fetchTrelloBoards().stream()
                .filter(trelloBoardDto -> trelloBoardDto.getName().toLowerCase().contains(filter.toLowerCase()))
                .collect(Collectors.toList());
    }

    @PostMapping(value = "cards")
    public CreatedTrelloCardDto createNewCard(@RequestBody TrelloCardDto trelloCardDto){
        return trelloFacade.createCard(trelloCardDto);
    }
}
