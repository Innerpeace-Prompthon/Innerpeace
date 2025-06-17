package com.innerpeace.innerpeace.travelpurpose;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@Service
public class TravelPurposeService {
    private final RestTemplate restTemplate = new RestTemplate();
    private final ObjectMapper objectMapper = new ObjectMapper();

    public TravelPurposeResponseDto requestChatCompletion(String userInput) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("project", "KNTO-PROMPTON-270");
        headers.set("apiKey", "");
        headers.set("Content-Type", "application/json; charset=utf-8");

        Map<String, Object> request = Map.of(
                "hash", "",
                "params", Map.of(
                        "user_input", userInput
                )
        );
        HttpEntity<Map<String, Object>> entity = new HttpEntity<>(request, headers);

        ResponseEntity<String> response = restTemplate.exchange(
                "https://api-laas.wanted.co.kr/api/preset/v2/chat/completions",
                HttpMethod.POST,
                entity,
                String.class
        );

        try {
            // 전체 JSON 응답을 트리 형태로 파싱
            JsonNode root = objectMapper.readTree(response.getBody());

            // content 필드(문자열로 된 JSON)를 꺼내기
            String contentJsonString = root
                    .path("choices")
                    .get(0)
                    .path("message")
                    .path("content")
                    .asText();

            // content 문자열을 DTO로 파싱
            TravelPurposeResponseDto travelPurposeResponseDto = objectMapper.readValue(contentJsonString, TravelPurposeResponseDto.class);

            return travelPurposeResponseDto;

        } catch (Exception e) {
            throw new RuntimeException("응답 파싱 실패", e);
        }
    }
}
