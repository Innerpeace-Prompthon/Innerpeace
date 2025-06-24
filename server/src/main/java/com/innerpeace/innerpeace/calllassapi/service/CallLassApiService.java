package com.innerpeace.innerpeace.calllassapi.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.innerpeace.innerpeace.calllassapi.dto.CallLassApiRequestDto;
import com.innerpeace.innerpeace.calllassapi.dto.ResponseDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class CallLassApiService {

    @Value("${laas.api.key}")
    private String apiKey;

    @Value("${laas.project.id}")
    private String projectId;

    private final ObjectMapper objectMapper = new ObjectMapper();

    public ResponseDto requestChatCompletion(CallLassApiRequestDto requestDto) throws JsonProcessingException{
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


        return getText(response);

    }

    private ResponseDto getText(ResponseEntity<String> response) throws JsonProcessingException {
        JsonNode root = objectMapper.readTree(response.getBody());
        String contentJsonString = root
                .path("choices")
                .get(0)
                .path("message")
                .path("content")
                .asText();

        return parseTextToResponseDto(contentJsonString);
    }

    private ResponseDto parseTextToResponseDto(String text) {
        List<ResponseDto.TravelDay> travelDays = new ArrayList<>();
        ResponseDto.TravelDay currentDay = null;
        List<ResponseDto.Plan> currentPlans = new ArrayList<>();

        String[] lines = text.split("\\r?\\n");
        ResponseDto.Plan currentPlan = null;

        for (String line : lines) {
            line = line.trim();

            if (line.startsWith("Day")) {
                if (currentDay != null) {
                    currentDay.setPlan(currentPlans);
                    travelDays.add(currentDay);
                    currentPlans = new ArrayList<>();
                }

                String[] parts = line.split(" - ");
                currentDay = new ResponseDto.TravelDay();
                currentDay.setDay(parts[0].trim());
                currentDay.setDate(parts.length > 1 ? parts[1].trim() : "");
            } else if (line.startsWith("순서:")) {
                currentPlan = new ResponseDto.Plan();
                currentPlan.setOrder(Integer.parseInt(line.replace("순서:", "").trim()));
            } else if (line.startsWith("장소:")) {
                currentPlan.setPlace(line.replace("장소:", "").trim());
            } else if (line.startsWith("활동:")) {
                currentPlan.setActivity(line.replace("활동:", "").trim());
            } else if (line.startsWith("설명:")) {
                currentPlan.setDescription(line.replace("설명:", "").trim());
            } else if (line.startsWith("이미지:")) {
                currentPlan.setImage(line.replace("이미지:", "").trim());
            } else if (line.startsWith("위도:")) {
                try {
                    currentPlan.setLatitude(Double.parseDouble(line.replace("위도:", "").trim()));
                } catch (NumberFormatException e) {
                    currentPlan.setLatitude(0.0);
                }
            } else if (line.startsWith("경도:")) {
                try {
                    currentPlan.setLongitude(Double.parseDouble(line.replace("경도:", "").trim()));
                } catch (NumberFormatException e) {
                    currentPlan.setLongitude(0.0);
                }
                currentPlans.add(currentPlan);
            }
        }

        if (currentDay != null) {
            currentDay.setPlan(currentPlans);
            travelDays.add(currentDay);
        }

        ResponseDto responseDto = new ResponseDto();
        responseDto.setText(text);           // 원본 텍스트 그대로 넣기
        responseDto.setTravelSchedule(travelDays);  // 파싱한 일정 리스트 넣기
        return responseDto;
    }



}