package com.crud.tasks.domain;

import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class Mail {
    @NonNull
    private String receiverEmail;
    @NonNull
    private String subject;
    @NonNull
    private String message;

    private String[] ccs;

    public Mail(@NonNull String receiverEmail, @NonNull String subject, @NonNull String message, String... ccs) {
        this.receiverEmail = receiverEmail;
        this.subject = subject;
        this.message = message;
        this.ccs = ccs;
    }
}
