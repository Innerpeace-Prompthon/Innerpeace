package com.innerpeace.innerpeace.utils;

import com.innerpeace.innerpeace.utils.ParseRequestDto;
import com.innerpeace.innerpeace.utils.ParseResponseDto;
import com.innerpeace.innerpeace.utils.RawJsonResponseDto;
import com.innerpeace.innerpeace.utils.LaaSResponseParser;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * LaaS 응답 파싱 서비스 구현체
 * 실제 파싱 비즈니스 로직 처리
 */
@Service
@Slf4j
public class ResponseParserServiceImpl implements ResponseParserService {

    @Override
    public ParseResponseDto parseToPlaces(ParseRequestDto request) {
        log.debug("장소 파싱 서비스 시작 - 요청 텍스트: {}",
                request.getText() != null ? request.getText().substring(0, Math.min(100, request.getText().length())) + "..." : "null");

        try {
            // 입력 검증
            if (request.getText() == null || request.getText().trim().isEmpty()) {
                return ParseResponseDto.builder()
                        .success(false)
                        .message("파싱할 텍스트가 비어있습니다.")
                        .data(null)
                        .build();
            }

            // 파싱 실행
            LaaSResponseParser.PlaceResponse placeResponse =
                    LaaSResponseParser.parseResponse(request.getText());

            log.info("장소 파싱 완료 - 추출된 장소 수: {}", placeResponse.getTotalCount());

            return ParseResponseDto.builder()
                    .success(true)
                    .message("파싱 성공")
                    .data(placeResponse)
                    .build();

        } catch (Exception e) {
            log.error("장소 파싱 오류: {}", e.getMessage(), e);

            return ParseResponseDto.builder()
                    .success(false)
                    .message("파싱 중 오류가 발생했습니다: " + e.getMessage())
                    .data(null)
                    .build();
        }
    }

    @Override
    public RawJsonResponseDto parseToJson(ParseRequestDto request) {
        log.debug("Raw JSON 파싱 서비스 시작 - 요청 텍스트: {}",
                request.getText() != null ? request.getText().substring(0, Math.min(100, request.getText().length())) + "..." : "null");

        try {
            // 입력 검증
            if (request.getText() == null || request.getText().trim().isEmpty()) {
                return RawJsonResponseDto.builder()
                        .success(false)
                        .message("파싱할 텍스트가 비어있습니다.")
                        .jsonData("{\"places\":[],\"totalCount\":0}")
                        .build();
            }

            // JSON 파싱 실행
            String jsonResult = LaaSResponseParser.parseToJson(request.getText());

            log.info("Raw JSON 파싱 완료 - JSON 길이: {}", jsonResult.length());

            return RawJsonResponseDto.builder()
                    .success(true)
                    .message("JSON 파싱 성공")
                    .jsonData(jsonResult)
                    .build();

        } catch (Exception e) {
            log.error("Raw JSON 파싱 오류: {}", e.getMessage(), e);

            return RawJsonResponseDto.builder()
                    .success(false)
                    .message("JSON 파싱 중 오류가 발생했습니다: " + e.getMessage())
                    .jsonData("{\"error\":\"parsing_failed\"}")
                    .build();
        }
    }
}