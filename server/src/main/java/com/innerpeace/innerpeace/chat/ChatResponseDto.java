package com.innerpeace.innerpeace.chat;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 채팅 응답 DTO
 */
@Data
@NoArgsConstructor
public class ChatResponseDto {
    private String message;
    private String status;
    private long timestamp;

    public ChatResponseDto(String message, String status) {
        this.message = message;
        this.status = status;
        this.timestamp = System.currentTimeMillis();
    }
}