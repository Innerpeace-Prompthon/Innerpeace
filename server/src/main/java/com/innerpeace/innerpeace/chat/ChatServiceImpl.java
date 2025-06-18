package com.innerpeace.innerpeace.chat;

import com.innerpeace.innerpeace.laas.LaasService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * 채팅 서비스 구현체
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class ChatServiceImpl implements ChatService {

    private final LaasService laasService;

    @Override
    public String generateResponse(String userMessage) throws Exception {
        log.debug("채팅 응답 생성 시작 - 사용자 메시지: {}", userMessage);

        try {
            // 입력 검증
            if (userMessage == null || userMessage.trim().isEmpty()) {
                throw new IllegalArgumentException("사용자 메시지가 비어있습니다.");
            }

            // LaaS API를 통한 응답 생성
            String aiResponse = laasService.callLaaSPreset(userMessage.trim());

            log.debug("채팅 응답 생성 완료 - 응답 길이: {}", aiResponse.length());
            return aiResponse;

        } catch (Exception e) {
            log.error("채팅 응답 생성 실패 - 사용자 메시지: {}, 오류: {}", userMessage, e.getMessage(), e);
            throw e;
        }
    }
}