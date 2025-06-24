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
        ResponseDto.Plan currentPlan = null;

        String[] lines = text.split("\\r?\\n");
        for (String line : lines) {
            line = line.trim();

            if (line.startsWith("Day")) {
                if (currentDay != null) {
                    currentDay.setPlan(currentPlans);
                    travelDays.add(currentDay);
                    currentPlans = new ArrayList<>();
                }
                currentDay = parseTravelDay(line);

            } else if (line.startsWith("순서:")) {
                currentPlan = new ResponseDto.Plan();
                currentPlan.setOrder(parseIntValue(line, "순서:"));

            } else if (currentPlan != null) {
                parsePlanProperty(currentPlan, line);
                if (line.startsWith("경도:")) {
                    currentPlans.add(currentPlan);
                }
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

    private static ResponseDto.TravelDay parseTravelDay(String line) {
        String[] parts = line.split(" - ");
        ResponseDto.TravelDay day = new ResponseDto.TravelDay();
        day.setDay(parts[0].trim());
        day.setDate(parts.length > 1 ? parts[1].trim() : "");
        return day;
    }

    private static int parseIntValue(String line, String prefix) {
        try {
            return Integer.parseInt(line.replace(prefix, "").trim());
        } catch (NumberFormatException e) {
            return 0;
        }
    }

    private static double parseDoubleValue(String line, String prefix) {
        try {
            return Double.parseDouble(line.replace(prefix, "").trim());
        } catch (NumberFormatException e) {
            return 0.0;
        }
    }

    private static void parsePlanProperty(ResponseDto.Plan plan, String line) {
        if (line.startsWith("장소:")) {
            plan.setPlace(line.replace("장소:", "").trim());
        } else if (line.startsWith("활동:")) {
            plan.setActivity(line.replace("활동:", "").trim());
        } else if (line.startsWith("설명:")) {
            plan.setDescription(line.replace("설명:", "").trim());
        } else if (line.startsWith("이미지:")) {
            plan.setImage(line.replace("이미지:", "").trim());
        } else if (line.startsWith("위도:")) {
            plan.setLatitude(parseDoubleValue(line, "위도:"));
        } else if (line.startsWith("경도:")) {
            plan.setLongitude(parseDoubleValue(line, "경도:"));
        }
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
