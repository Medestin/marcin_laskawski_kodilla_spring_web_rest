package com.crud.tasks.controller;

import com.crud.tasks.domain.TrelloBoardDto;
import com.crud.tasks.trello.client.TrelloClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/v1/trello")
public class TrelloController {
    private TrelloClient trelloClient;

    @Autowired
    public TrelloController(TrelloClient trelloClient) {
        this.trelloClient = trelloClient;
    }

    @GetMapping(value = "boards")
    public List<TrelloBoardDto> getTrelloBoards(){
        return new ArrayList<>(trelloClient.getTrelloBoards());
    }

    @GetMapping(value = "boards/{filter}")
    public List<TrelloBoardDto> getTrelloBoards(@PathVariable String filter) {
        return trelloClient.getTrelloBoards().stream()
                .filter(trelloBoardDto -> trelloBoardDto.getName().toLowerCase().contains(filter.toLowerCase()))
                .collect(Collectors.toList());
    }
}
