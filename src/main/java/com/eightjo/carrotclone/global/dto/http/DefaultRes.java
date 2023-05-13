package com.eightjo.carrotclone.global.dto.http;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DefaultRes<T> {
    private String responseMessage;

    public DefaultRes(final String responseMessage) {
        this.responseMessage = responseMessage;
    }
}

