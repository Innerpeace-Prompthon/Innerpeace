package com.innerpeace.innerpeace.laas;

import com.innerpeace.innerpeace.laas.LaasService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;

/**
 * LaaS API 서비스 구현체
 */
@Service
@Slf4j
public class LaasServiceImpl implements LaasService {

    private static final String LAAS_API_URL = "https://api-laas.wanted.co.kr/api/preset/v2/chat/completions";

    @Value("${laas.project.id}")
    private String projectId;

    @Value("${laas.api.key}")
    private String apiKey;

    @Value("${laas.preset.hash}")
    private String presetHash;

    private final RestTemplate restTemplate;

    public LaasServiceImpl() {
        this.restTemplate = new RestTemplate();
        // UTF-8 인코딩 설정
        restTemplate.getMessageConverters()
                .add(0, new StringHttpMessageConverter(StandardCharsets.UTF_8));
    }

    @Override
    public String callLaaSPreset(String userMessage) throws Exception {
        log.debug("LaaS API 호출 시작 - 메시지: {}", userMessage);

        try {
            var requestBody = buildRequestBody(userMessage);
            var headers = buildHeaders();
            var request = new HttpEntity<>(requestBody, headers);

            ResponseEntity<Map> response = restTemplate.postForEntity(
                    LAAS_API_URL,
                    request,
                    Map.class
            );

            if (response.getStatusCode() == HttpStatus.OK && response.getBody() != null) {
                String aiResponse = extractAnswerFromResponse(response.getBody());
                String cleanedResponse = cleanAndFormatResponse(aiResponse);

                log.debug("LaaS API 호출 성공 - 응답 길이: {}", cleanedResponse.length());
                return cleanedResponse;
            } else {
                throw new Exception("LaaS API 호출 실패: " + response.getStatusCode());
            }

        } catch (Exception e) {
            log.error("LaaS API 호출 오류: {}", e.getMessage(), e);
            return createFallbackResponse(userMessage);
        }
    }

    /**
     * 요청 바디 구성
     */
    private Map<String, Object> buildRequestBody(String userMessage) {
        var params = Map.of(
                "user_situation", extractSituation(userMessage),
                "location", extractLocation(userMessage),
                "duration", extractDuration(userMessage),
                "companion", extractCompanion(userMessage),
                "preference", userMessage
        );

        var userMsg = Map.of(
                "role", "user",
                "content", userMessage
        );

        return Map.of(
                "hash", presetHash,
                "params", params,
                "messages", List.of(userMsg)
        );
    }

    /**
     * HTTP 헤더 구성
     */
    private HttpHeaders buildHeaders() {
        var headers = new HttpHeaders();
        headers.setContentType(new MediaType("application", "json", StandardCharsets.UTF_8));
        headers.setAcceptCharset(List.of(StandardCharsets.UTF_8));
        headers.set("project", projectId);
        headers.set("apiKey", apiKey);
        return headers;
    }

    /**
     * 사용자 입력에서 상황 정보 추출
     */
    private String extractSituation(String message) {
        if (message.contains("스트레스") || message.contains("피곤") || message.contains("지쳐")) {
            return "일상 스트레스로 지친 상황";
        }
        return "휴식이 필요한 상황";
    }

    private String extractLocation(String message) {
        // TODO: 향후 NLP를 통한 지역 추출 로직 구현
        return "서울";
    }

    private String extractDuration(String message) {
        if (message.contains("1박") || message.contains("숙박")) {
            return "1박2일";
        }
        return "당일";
    }

    private String extractCompanion(String message) {
        if (message.contains("혼자")) return "혼자";
        if (message.contains("가족")) return "가족과";
        if (message.contains("친구")) return "친구와";
        return "혼자";
    }

    /**
     * LaaS 응답에서 답변 추출
     */
    private String extractAnswerFromResponse(Map<String, Object> response) {
        try {
            if (response.containsKey("choices") && response.get("choices") instanceof List<?> choicesList) {
                if (!choicesList.isEmpty() && choicesList.get(0) instanceof Map<?, ?> choice) {
                    if (choice.containsKey("message") && choice.get("message") instanceof Map<?, ?> message) {
                        if (message.containsKey("content")) {
                            return message.get("content").toString();
                        }
                    }
                }
            }
            return "응답을 파싱할 수 없습니다.";
        } catch (Exception e) {
            log.error("응답 파싱 오류: {}", e.getMessage(), e);
            return "응답 파싱 중 오류가 발생했습니다.";
        }
    }

    /**
     * 텍스트 정리 및 포맷팅
     */
    private String cleanAndFormatResponse(String content) {
        if (content == null || content.isEmpty()) {
            return content;
        }

        return content.trim()
                .replaceAll("\\s+", " ")
                .replaceAll("###\\s*([^\\n]+)", "\n\n### $1\n")
                .replaceAll("\\*\\*([^*]+)\\*\\*", "\n\n**$1**\n")
                .replaceAll("([📍🚗⏰💰📞🌿😊💭])", "\n$1")
                .replaceAll("\n{3,}", "\n\n")
                .replaceAll("^\n+", "")
                .replaceAll("\n+$", "");
    }

    /**
     * 폴백 응답 생성
     */
    private String createFallbackResponse(String userMessage) {
        return String.format("""
                안녕하세요! 😊
                
                현재 여행 추천 서비스에 일시적인 문제가 있습니다.
                
                **'%s'**에 대한 맞춤 여행 계획을 준비하고 있어요.
                잠시 후 다시 시도해주세요!
                
                ### 💡 다시 시도해보세요
                더 구체적인 정보를 알려주시면 더 좋은 추천을 드릴 수 있어요:
                
                📍 가고 싶은 지역
                ⏰ 여행 기간 (당일/1박2일 등)  
                👥 동반자 (혼자/가족/친구/연인)
                """, userMessage);
    }
}