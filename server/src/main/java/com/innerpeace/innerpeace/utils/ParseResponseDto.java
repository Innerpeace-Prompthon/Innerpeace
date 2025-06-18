package com.innerpeace.innerpeace.utils;

import com.innerpeace.innerpeace.utils.LaaSResponseParser;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 구조화된 장소 데이터 파싱 응답 DTO
 * 클라이언트에게 파싱된 장소 정보를 전달할 때 사용
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ParseResponseDto {

    /**
     * 파싱 성공 여부
     */
    private boolean success;

    /**
     * 응답 메시지 (성공/실패 설명)
     */
    private String message;

    /**
     * 파싱된 장소 데이터
     * 파싱 실패 시 null
     */
    private LaaSResponseParser.PlaceResponse data;
}