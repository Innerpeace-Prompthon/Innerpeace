package com.innerpeace.innerpeace.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * LaaS 응답에서 장소 정보만 간단히 추출하는 유틸리티
 * Tour API 요청을 위한 장소명과 주소만 파싱
 */
@Slf4j
public class LaaSResponseParser {

    private static final ObjectMapper objectMapper = new ObjectMapper();

    // 텍스트 정리용 패턴
    private static final Pattern EMOJI_PATTERN = Pattern.compile("[\\p{So}\\p{Sk}\\u2600-\\u27FF\\uFE0F]");
    private static final Pattern MARKDOWN_PATTERN = Pattern.compile("\\*\\*|###|━━━|`|\\*|#");

    // 장소명 추출 패턴 (다양한 형태 지원)
    private static final Pattern PLACE_NAME_PATTERNS = Pattern.compile(
            "(?:^|\\n)\\s*(?:\\d+\\.\\s*)?(?:\\*\\*)?([가-힣a-zA-Z\\s]{2,20})(?:\\*\\*)?\\s*(?:\\n|$)"
    );

    // 주소 추출 패턴 (한국 주소 형태)
    private static final Pattern ADDRESS_PATTERN = Pattern.compile(
            "([가-힣]+(?:시|도)\\s+[가-힣]+(?:구|군)\\s+[가-힣\\s\\d-]+(?:동|리|로|길)[\\s\\d가-힣-]*)"
    );

    /**
     * LaaS 응답을 장소 정보 JSON으로 변환
     */
    public static String parseToJson(String laasResponse) {
        try {
            PlaceResponse response = parseResponse(laasResponse);
            return objectMapper.writeValueAsString(response);
        } catch (Exception e) {
            log.error("장소 정보 파싱 실패: {}", e.getMessage(), e);
            return createErrorJson();
        }
    }

    /**
     * LaaS 응답에서 장소 정보 추출
     */
    public static PlaceResponse parseResponse(String laasResponse) {
        if (laasResponse == null || laasResponse.trim().isEmpty()) {
            return PlaceResponse.builder()
                    .places(new ArrayList<>())
                    .totalCount(0)
                    .build();
        }

        String cleanedText = cleanText(laasResponse);
        List<Place> places = extractPlaces(cleanedText);

        return PlaceResponse.builder()
                .places(places)
                .totalCount(places.size())
                .build();
    }

    /**
     * 텍스트 정리 (이모지, 마크다운 제거)
     */
    private static String cleanText(String text) {
        return text
                .replaceAll(EMOJI_PATTERN.pattern(), "")
                .replaceAll(MARKDOWN_PATTERN.pattern(), "")
                .replaceAll("\\s+", " ")
                .trim();
    }

    /**
     * 장소 정보 추출 (최대 5곳)
     */
    private static List<Place> extractPlaces(String text) {
        List<Place> places = new ArrayList<>();
        Set<String> addedPlaces = new HashSet<>(); // 중복 방지

        // 방법 1: ** 패턴으로 구분된 섹션들 처리
        extractFromBoldSections(text, places, addedPlaces);

        // 방법 2: 위치 정보 기준으로 섹션 나누기
        if (places.size() < 3) {
            extractFromLocationSections(text, places, addedPlaces);
        }

        // 방법 3: 줄바꿈 기준으로 처리
        if (places.size() < 2) {
            extractFromLineSections(text, places, addedPlaces);
        }

        return places;
    }

    /**
     * ** 패턴으로 구분된 섹션에서 장소 추출
     */
    private static void extractFromBoldSections(String text, List<Place> places, Set<String> addedPlaces) {
        // **텍스트** 패턴으로 분할
        String[] sections = text.split("\\*\\*");

        for (int i = 1; i < sections.length && places.size() < 5; i += 2) {
            if (i + 1 < sections.length) {
                String placeName = sections[i].trim();
                String content = sections[i + 1];

                if (isValidPlaceName(placeName)) {
                    String address = extractAddress(content);
                    if (!address.isEmpty() && !addedPlaces.contains(placeName)) {
                        places.add(Place.builder()
                                .name(cleanPlaceName(placeName))
                                .address(address)
                                .build());
                        addedPlaces.add(placeName);
                    }
                }
            }
        }
    }

    /**
     * 위치 정보 기준으로 섹션 나누어 장소 추출
     */
    private static void extractFromLocationSections(String text, List<Place> places, Set<String> addedPlaces) {
        // "위치:" 기준으로 분할
        String[] sections = text.split("(?=위치[:\\s])");

        for (String section : sections) {
            if (places.size() >= 5) break;

            String placeName = extractPlaceName(section);
            String address = extractAddress(section);

            if (!placeName.isEmpty() && !address.isEmpty() &&
                    !addedPlaces.contains(placeName)) {

                places.add(Place.builder()
                        .name(placeName)
                        .address(address)
                        .build());

                addedPlaces.add(placeName);
            }
        }
    }

    /**
     * 줄 단위로 처리하여 장소 추출
     */
    private static void extractFromLineSections(String text, List<Place> places, Set<String> addedPlaces) {
        String[] lines = text.split("\\n");
        String currentSection = "";

        for (String line : lines) {
            if (places.size() >= 5) break;

            // 새로운 장소 시작 감지 (관광지 키워드 포함)
            if (line.matches(".*[가-힣]{2,}(?:폭포|산|강|공원|박물관|미술관|전시관|호수|해변|섬|마을|도|궁|탑|사찰|교회|등대|분지|휴양림|타워|광장|시장|다리|터널|동굴|계곡|해안|항구|역|읍성|성곽).*")) {

                // 이전 섹션 처리
                if (!currentSection.isEmpty()) {
                    processSection(currentSection, places, addedPlaces);
                }
                currentSection = line;
            } else {
                currentSection += " " + line;
            }
        }

        // 마지막 섹션 처리
        if (!currentSection.isEmpty() && places.size() < 5) {
            processSection(currentSection, places, addedPlaces);
        }
    }

    /**
     * 문단에서 장소명 추출
     */
    private static String extractPlaceName(String paragraph) {
        // 범용적인 패턴들 - 하드코딩 제거
        List<Pattern> patterns = Arrays.asList(
                // 1. ** 안에 있는 장소명 (가장 일반적)
                Pattern.compile("\\*\\*([가-힣a-zA-Z\\s]{2,20})\\*\\*"),

                // 2. 관광지 키워드가 포함된 장소명
                Pattern.compile("([가-힣\\s]{2,20}(?:폭포|산|강|공원|박물관|미술관|전시관|호수|해변|섬|마을|도|궁|탑|사찰|교회|등대|분지|휴양림|타워|광장|시장|다리|터널|동굴|계곡|해안|항구|역|읍성|성곽))"),

                // 3. 위치 정보 바로 앞에 나오는 장소명
                Pattern.compile("([가-힣a-zA-Z\\s]{2,20})\\s*(?:위치|주소)[:\\s]"),

                // 4. 줄 시작 부분의 장소명 (번호나 기호 뒤)
                Pattern.compile("(?:^|\\n)\\s*(?:\\d+\\.\\s*)?([가-힣a-zA-Z\\s]{2,20})\\s*(?:\\n|$)"),

                // 5. 인용부호나 괄호 안의 장소명
                Pattern.compile("[\"'「]([가-힣a-zA-Z\\s]{2,20})[\"'」]"),
                Pattern.compile("\\[([가-힣a-zA-Z\\s]{2,20})\\]")
        );

        for (Pattern pattern : patterns) {
            Matcher matcher = pattern.matcher(paragraph);
            if (matcher.find()) {
                String name = matcher.group(1).trim();
                // 불필요한 접미사/접두사 제거
                name = cleanPlaceName(name);
                if (isValidPlaceName(name)) {
                    return name;
                }
            }
        }

        return "";
    }

    /**
     * 장소명 정리 (불필요한 단어 제거)
     */
    private static String cleanPlaceName(String name) {
        return name
                .replaceAll("^(추천|장소|여행|관광|명소|코스)\\s*", "") // 앞쪽 불필요 단어
                .replaceAll("\\s*(지역|구역|일대|근처|주변|안내|소개|정보)$", "") // 뒤쪽 불필요 단어
                .replaceAll("\\s+", " ") // 연속 공백 제거
                .trim();
    }

    /**
     * 문단에서 주소 추출
     */
    private static String extractAddress(String paragraph) {
        // 위치 키워드 이후의 주소만 정확히 추출
        List<Pattern> patterns = Arrays.asList(
                Pattern.compile("위치[:\\s]*([가-힣]+(?:시|도|군|구)\\s+[가-힣]+(?:면|동|로|리)\\s*[가-힣\\d\\s-]*?)\\s*(?:교통|🚗|\\n|$)"),
                Pattern.compile("주소[:\\s]*([가-힣]+(?:시|도|군|구)\\s+[가-힣]+(?:면|동|로|리)\\s*[가-힣\\d\\s-]*?)\\s*(?:교통|🚗|\\n|$)"),
                Pattern.compile("([가-힣]+(?:군|구)\\s+[가-힣]+(?:면|읍|동)\\s+[가-힣]+리?\\s*[가-힣\\d\\s-]*?)\\s*(?:교통|🚗|\\n|$)")
        );

        for (Pattern pattern : patterns) {
            Matcher matcher = pattern.matcher(paragraph);
            if (matcher.find()) {
                String address = matcher.group(1).trim();
                // 주소 끝에 불필요한 단어들 제거
                address = address.replaceAll("\\s+(교통|버스|도보|차량).*$", "").trim();
                if (address.length() > 5 && address.length() < 50) {
                    return address;
                }
            }
        }

        return "";
    }

    /**
     * 유효한 장소명인지 검증
     */
    private static boolean isValidPlaceName(String name) {
        return name.length() >= 2 &&
                name.length() <= 20 &&
                !name.matches("^[\\d\\s\\p{P}]+$") && // 숫자/기호만 있는 경우 제외
                !Arrays.asList("추천", "장소", "여행", "정보", "안내").contains(name);
    }

    /**
     * 개별 섹션 처리
     */
    private static void processSection(String section, List<Place> places, Set<String> addedPlaces) {
        String placeName = extractPlaceName(section);
        String address = extractAddress(section);

        if (!placeName.isEmpty() && !address.isEmpty() &&
                !addedPlaces.contains(placeName) && places.size() < 5) {

            places.add(Place.builder()
                    .name(placeName)
                    .address(address)
                    .build());

            addedPlaces.add(placeName);
        }
    }

    /**
     * 에러 발생 시 빈 응답 JSON 생성
     */
    private static String createErrorJson() {
        try {
            PlaceResponse errorResponse = PlaceResponse.builder()
                    .places(new ArrayList<>())
                    .totalCount(0)
                    .build();
            return objectMapper.writeValueAsString(errorResponse);
        } catch (Exception e) {
            return "{\"places\":[],\"totalCount\":0}";
        }
    }

    /**
     * 장소 응답 데이터 구조
     */
    @lombok.Data
    @lombok.Builder
    public static class PlaceResponse {
        private List<Place> places;
        private int totalCount;
    }

    /**
     * 장소 정보 데이터 구조
     */
    @lombok.Data
    @lombok.Builder
    public static class Place {
        private String name;    // 장소명
        private String address; // 주소
    }
}