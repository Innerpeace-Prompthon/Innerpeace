package com.innerpeace.innerpeace.calllassapi.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.innerpeace.innerpeace.calllassapi.dto.CallLassApiRequestDto;
import com.innerpeace.innerpeace.calllassapi.dto.ResponseDto;
import com.innerpeace.innerpeace.util.ResponseParser;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@Service
public class CallLassApiService {

    @Value("${laas.api.key}")
    private String apiKey;

    @Value("${laas.project.id}")
    private String projectId;


    public ResponseDto requestChatCompletion(CallLassApiRequestDto requestDto) throws JsonProcessingException {
        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.set("project", projectId);
        headers.set("apiKey", apiKey);
        headers.set("Content-Type", "application/json; charset=utf-8");

        Map<String, Object> request = Map.of(
                "hash", "7d58dfb6910ce0f5a240769143b43a62c65e1b716820632dff4761e19b0b1d49",
                "params", Map.of(
                        "유저입력", requestDto.getUserInput(),
                        "여행날짜", requestDto.getDate(),
                        "선호지역", requestDto.getRegion(),
                        "선호여행타입", requestDto.getTravelType(),
                        "이동수단", requestDto.getTransportation()
                )
        );
        HttpEntity<Map<String, Object>> entity = new HttpEntity<>(request, headers);

        ResponseEntity<String> response = restTemplate.exchange(
                "https://api-laas.wanted.co.kr/api/preset/v2/chat/completions",
                HttpMethod.POST,
                entity,
                String.class
        );

        return ResponseParser.parseResponseEntity(response);

    }
}