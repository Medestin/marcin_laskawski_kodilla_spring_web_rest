package com.crud.tasks.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public final class TaskDto {
    private Long id;
    private String title;
    private String content;
}
