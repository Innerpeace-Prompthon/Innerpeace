package com.innerpeace.innerpeace.chat;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 채팅 요청 DTO
 */
@Data
@NoArgsConstructor
public class ChatRequestDto {
    private String message;

    public ChatRequestDto(String message) {
        this.message = message;
    }
}