package com.innerpeace.innerpeace.utils;

import com.innerpeace.innerpeace.utils.ParseRequestDto;
import com.innerpeace.innerpeace.utils.ParseResponseDto;
import com.innerpeace.innerpeace.utils.RawJsonResponseDto;

/**
 * LaaS 응답 파싱 서비스 인터페이스
 */
public interface ResponseParserService {

    /**
     * LaaS 응답 텍스트를 구조화된 장소 정보로 파싱
     *
     * @param request 파싱 요청 DTO
     * @return 파싱된 장소 정보 응답 DTO
     */
    ParseResponseDto parseToPlaces(ParseRequestDto request);

    /**
     * LaaS 응답 텍스트를 Raw JSON 문자열로 파싱
     *
     * @param request 파싱 요청 DTO
     * @return Raw JSON 응답 DTO
     */
    RawJsonResponseDto parseToJson(ParseRequestDto request);
}