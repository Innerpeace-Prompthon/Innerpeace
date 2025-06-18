package com.innerpeace.innerpeace.utils;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Raw JSON 문자열 파싱 응답 DTO
 * 고급 사용자가 JSON 문자열 형태로 파싱 결과를 받을 때 사용
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RawJsonResponseDto {

    /**
     * 파싱 성공 여부
     */
    private boolean success;

    /**
     * 응답 메시지 (성공/실패 설명)
     */
    private String message;

    /**
     * 파싱된 JSON 문자열
     * 파싱 실패 시 에러 JSON
     */
    private String jsonData;
}