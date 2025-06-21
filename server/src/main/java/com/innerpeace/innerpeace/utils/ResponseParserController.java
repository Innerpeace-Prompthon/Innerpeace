package com.innerpeace.innerpeace.utils;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/parse")
@RequiredArgsConstructor
public class ResponseParserController {

    private final ResponseParserService responseParserService;

    @PostMapping
    public ResponseEntity<ParseResponseDto> parse(@RequestBody ParseRequestDto request) {
        return ResponseEntity.ok(responseParserService.parse(request));
    }
}