package com.crud.tasks.domain;

import com.crud.tasks.domain.badges.Badges;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class CreatedTrelloCardDto {
    private String id;
    private String name;
    private String shortUrl;
    private Badges badges;
}
