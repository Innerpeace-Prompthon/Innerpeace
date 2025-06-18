package com.innerpeace.innerpeace.utils;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 파싱 요청 DTO
 * 클라이언트에서 파싱할 텍스트를 전달할 때 사용
 */
@Data
@NoArgsConstructor
public class ParseRequestDto {

    /**
     * 파싱할 LaaS 응답 텍스트
     */
    private String text;

    public ParseRequestDto(String text) {
        this.text = text;
    }
}