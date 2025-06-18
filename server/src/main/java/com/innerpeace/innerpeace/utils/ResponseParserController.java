package com.innerpeace.innerpeace.utils;

import com.innerpeace.innerpeace.utils.ParseRequestDto;
import com.innerpeace.innerpeace.utils.ParseResponseDto;
import com.innerpeace.innerpeace.utils.RawJsonResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * LaaS 응답 파싱 컨트롤러
 * HTTP 요청/응답 처리만 담당
 */
@RestController
@RequestMapping("/api/parse")
@CrossOrigin(origins = {"http://localhost:5173", "http://localhost:3000"}, allowCredentials = "true")
@RequiredArgsConstructor
@Slf4j
public class ResponseParserController {

    private final ResponseParserService responseParserService;

    /**
     * LaaS 응답 텍스트를 장소 정보로 파싱
     */
    @PostMapping("/places")
    public ResponseEntity<ParseResponseDto> parseToPlaces(@RequestBody ParseRequestDto request) {
        log.info("장소 파싱 요청 수신 - 텍스트 길이: {}",
                request.getText() != null ? request.getText().length() : 0);

        ParseResponseDto response = responseParserService.parseToPlaces(request);
        return ResponseEntity.ok(response);
    }

    /**
     * LaaS 응답 텍스트를 Raw JSON으로 파싱
     */
    @PostMapping("/json")
    public ResponseEntity<RawJsonResponseDto> parseToJson(@RequestBody ParseRequestDto request) {
        log.info("Raw JSON 파싱 요청 수신 - 텍스트 길이: {}",
                request.getText() != null ? request.getText().length() : 0);

        RawJsonResponseDto response = responseParserService.parseToJson(request);
        return ResponseEntity.ok(response);
    }

    /**
     * 파싱 API 헬스체크
     */
    @GetMapping("/health")
    public ResponseEntity<String> healthCheck() {
        return ResponseEntity.ok("Parser API is running");
    }

    /**
     * CORS preflight 요청 처리
     */
    @RequestMapping(method = RequestMethod.OPTIONS, value = "/**")
    public ResponseEntity<Void> handleOptions() {
        return ResponseEntity.ok().build();
    }
}