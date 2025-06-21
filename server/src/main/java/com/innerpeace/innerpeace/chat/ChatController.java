package com.innerpeace.innerpeace.chat;

import com.innerpeace.innerpeace.chat.ChatRequestDto;
import com.innerpeace.innerpeace.chat.ChatResponseDto;
import com.innerpeace.innerpeace.chat.ChatService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * 채팅 API 컨트롤러
 * - 요청/응답 처리만 담당
 * - 비즈니스 로직은 Service에 위임
 */
@RestController
@RequestMapping("/api/chat")
@CrossOrigin(origins = {"http://localhost:5173", "http://localhost:3000"}, allowCredentials = "true")
@RequiredArgsConstructor
@Slf4j
public class ChatController {

    private final ChatService chatService;

    /**
     * 채팅 메시지 처리
     */
    @PostMapping("/message")
    public ResponseEntity<ChatResponseDto> sendMessage(@RequestBody ChatRequestDto request) {
        log.info("채팅 요청 수신 - 메시지 길이: {}",
                request.getMessage() != null ? request.getMessage().length() : 0);

        try {
            String aiResponse = chatService.generateResponse(request.getMessage());
            return ResponseEntity.ok(new ChatResponseDto(aiResponse, "success"));

        } catch (IllegalArgumentException e) {
            log.warn("잘못된 요청: {}", e.getMessage());
            return ResponseEntity.ok(new ChatResponseDto("올바른 메시지를 입력해주세요.", "error"));

        } catch (Exception e) {
            log.error("채팅 처리 오류: {}", e.getMessage(), e);
            return ResponseEntity.ok(new ChatResponseDto("죄송합니다. 일시적인 오류가 발생했습니다. 잠시 후 다시 시도해주세요.", "error"));
        }
    }

    /**
     * CORS preflight 요청 처리
     */
    @RequestMapping(method = RequestMethod.OPTIONS, value = "/**")
    public ResponseEntity<Void> handleOptions() {
        return ResponseEntity.ok().build();
    }
}