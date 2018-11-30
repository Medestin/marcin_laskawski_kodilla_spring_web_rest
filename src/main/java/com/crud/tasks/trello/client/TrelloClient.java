package com.crud.tasks.trello.client;

import com.crud.tasks.domain.CreatedTrelloCard;
import com.crud.tasks.domain.TrelloBoardDto;
import com.crud.tasks.domain.TrelloCardDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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

    @Value("${trello.api.endpoint.prod}")
    private String trelloApiEndpoint;

    @Value("${trello.app.key}")
    private String trelloAppKey;

    @Value("${trello.app.token}")
    private String trelloToken;

    @Value("${trello.app.username}")
    private String username;

    @Autowired
    public TrelloClient(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
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
        return UriComponentsBuilder.fromHttpUrl(String.format("%s/members/%s/boards", trelloApiEndpoint, username))
                .queryParam("key", trelloAppKey)
                .queryParam("token", trelloToken)
                .queryParam("fields", "name,id")
                .queryParam("lists", "all")
                .build().encode().toUri();
    }

    public CreatedTrelloCard createNewCard(TrelloCardDto trelloCardDto){
        URI url = UriComponentsBuilder.fromHttpUrl(String.format("%s/cards", trelloApiEndpoint))
                .queryParam("key", trelloAppKey)
                .queryParam("token", trelloToken)
                .queryParam("name", trelloCardDto.getName())
                .queryParam("desc", trelloCardDto.getDescription())
                .queryParam("pos", trelloCardDto.getPos())
                .queryParam("idList", trelloCardDto.getListId())
                .build().encode().toUri();

        return restTemplate.postForObject(url, null, CreatedTrelloCard.class);
    }
}
