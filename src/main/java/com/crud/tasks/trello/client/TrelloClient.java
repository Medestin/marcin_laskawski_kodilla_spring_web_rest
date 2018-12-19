package com.crud.tasks.trello.client;

import com.crud.tasks.domain.CreatedTrelloCardDto;
import com.crud.tasks.domain.TrelloBoardDto;
import com.crud.tasks.domain.TrelloCardDto;
import com.crud.tasks.trello.config.TrelloConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Slf4j
@Component
public class TrelloClient {
    private RestTemplate restTemplate;
    private TrelloConfig trelloConfig;


    @Autowired
    public TrelloClient(RestTemplate restTemplate, TrelloConfig trelloConfig) {
        this.restTemplate = restTemplate;
        this.trelloConfig = trelloConfig;
    }

    public List<TrelloBoardDto> getTrelloBoards() {
        URI url = buildTrelloBoardsURI();

        try {
            TrelloBoardDto[] boardsResponse = Optional.ofNullable(restTemplate.getForObject(url, TrelloBoardDto[].class))
                    .orElseGet(() -> new TrelloBoardDto[0]);
            return Arrays.asList(boardsResponse);
        } catch (HttpClientErrorException e) {
            log.error(e.toString());
            return Collections.emptyList();
        }
    }

    private URI buildTrelloBoardsURI() {
        return UriComponentsBuilder.fromHttpUrl(String.format("%s/members/%s/boards", trelloConfig.getTrelloApiEndpoint(), trelloConfig.getUsername()))
                .queryParam("key", trelloConfig.getTrelloAppKey())
                .queryParam("token", trelloConfig.getTrelloToken())
                .queryParam("fields", "name,id")
                .queryParam("lists", "all")
                .build().encode().toUri();
    }

    public CreatedTrelloCardDto createNewCard(TrelloCardDto trelloCardDto){
        URI url = UriComponentsBuilder.fromHttpUrl(String.format("%s/cards", trelloConfig.getTrelloApiEndpoint()))
                .queryParam("key", trelloConfig.getTrelloAppKey())
                .queryParam("token", trelloConfig.getTrelloToken())
                .queryParam("name", trelloCardDto.getName())
                .queryParam("desc", trelloCardDto.getDescription())
                .queryParam("pos", trelloCardDto.getPos())
                .queryParam("idList", trelloCardDto.getListId())
                .build().encode().toUri();

        return restTemplate.postForObject(url, null, CreatedTrelloCardDto.class);
    }
}
