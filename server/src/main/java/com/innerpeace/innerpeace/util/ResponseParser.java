package com.innerpeace.innerpeace.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.innerpeace.innerpeace.calllassapi.dto.ResponseDto;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

public class ResponseParser {
    private static final ObjectMapper objectMapper = new ObjectMapper();

    private ResponseParser() {
        throw new UnsupportedOperationException("Utility class");
    }
    /*
    추출한 응답을 자바객체로 파싱하는 로직
     */
    public static ResponseDto parseTextToResponseDto(String text) {
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
        responseDto.setText(text);
        responseDto.setTravelSchedule(travelDays);
        return responseDto;
    }


    /*
    사용자에게 보여줄 응답 추출 로직
     */
    public static ResponseDto parseResponseEntity(ResponseEntity<String> response) throws JsonProcessingException {
        JsonNode root = objectMapper.readTree(response.getBody());
        String contentJsonString = root
                .path("choices")
                .get(0)
                .path("message")
                .path("content")
                .asText();

        return parseTextToResponseDto(contentJsonString);
    }
}
