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

    private static final String DAY = "Day";
    private static final String ORDER = "순서:";
    private static final String PLACE = "장소:";
    private static final String ACTIVITY = "활동:";
    private static final String DESCRIPTION = "설명:";
    private static final String ADDRESS = "주소:";
    private static final String IMAGE = "이미지:";
    private static final String LATITUDE = "위도:";
    private static final String LONGITUDE = "경도:";
    private static final String INNERPEACE = "innerpeace:";


    private ResponseParser() {
        throw new UnsupportedOperationException("Utility class");
    }

    public static ResponseDto parseTextToResponseDto(String text) {
        List<ResponseDto.TravelDay> travelDays = new ArrayList<>();
        ResponseDto.TravelDay currentDay = null;
        List<ResponseDto.Plan> currentPlans = new ArrayList<>();
        ResponseDto.Plan currentPlan = null;

        String[] lines = text.split("\\r?\\n");
        for (String line : lines) {
            line = line.trim();

            if (line.startsWith(DAY)) {
                if (currentDay != null) {
                    currentDay.setPlan(currentPlans);
                    travelDays.add(currentDay);
                    currentPlans = new ArrayList<>();
                }
                currentDay = parseTravelDay(line);

            } else if (line.startsWith(ORDER)) {
                currentPlan = new ResponseDto.Plan();
                currentPlan.setOrder(parseIntValue(line, ORDER));

            } else if (currentPlan != null) {
                parsePlanProperty(currentPlan, line);
                if (line.startsWith(LONGITUDE)) {
                    currentPlans.add(currentPlan);
                }
            }
        }


        if (currentDay != null) {
            currentDay.setPlan(currentPlans);
            travelDays.add(currentDay);
        }

        ResponseDto responseDto = new ResponseDto();
        responseDto.setText(extractInnerpeaceText(text));
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
        if (line.startsWith(PLACE)) {
            plan.setPlace(line.replace(PLACE, "").trim());
        } else if (line.startsWith(ACTIVITY)) {
            plan.setActivity(line.replace(ACTIVITY, "").trim());
        } else if (line.startsWith(DESCRIPTION)) {
            plan.setDescription(line.replace(DESCRIPTION, "").trim());
        } else if (line.startsWith(ADDRESS)) {
            plan.setAddress(line.replace(ADDRESS, "").trim());
        } else if (line.startsWith(IMAGE)) {
            plan.setImage(line.replace(IMAGE, "").trim());
        } else if (line.startsWith(LATITUDE)) {
            plan.setLatitude(parseDoubleValue(line, LATITUDE));
        } else if (line.startsWith(LONGITUDE)) {
            plan.setLongitude(parseDoubleValue(line, LONGITUDE));
        }
    }


    private static String extractInnerpeaceText(String fullText) {
        int idx = fullText.indexOf(INNERPEACE);
        if (idx == -1) return "";
        return fullText.substring(idx + INNERPEACE.length()).trim();
    }

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
