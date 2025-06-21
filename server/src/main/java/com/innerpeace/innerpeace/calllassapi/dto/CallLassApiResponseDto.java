package com.innerpeace.innerpeace.calllassapi.dto;

import lombok.Getter;

@Getter
public class CallLassApiResponseDto {
    private String output;

    public CallLassApiResponseDto(String output) {
        this.output = output;
    }
}