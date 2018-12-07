package com.crud.tasks.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class Mail {
    private String receiverEmail;
    private String subject;
    private String message;
}
