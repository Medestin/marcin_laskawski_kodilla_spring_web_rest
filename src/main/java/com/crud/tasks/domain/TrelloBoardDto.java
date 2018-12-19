package com.crud.tasks.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@JsonIgnoreProperties(ignoreUnknown = true)
public class TrelloBoardDto {
    @JsonProperty
    private String id;

    @JsonProperty
    private String name;

    @JsonProperty
    private List<TrelloListDto> lists;

    @Override
    public String toString() {
        return String.format("Board name: %s, id: %s", name, id);
    }
}
