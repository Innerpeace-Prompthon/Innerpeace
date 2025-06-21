package com.innerpeace.innerpeace.utils;

import com.innerpeace.innerpeace.utils.ParseRequestDto;
import com.innerpeace.innerpeace.utils.ParseResponseDto;
import com.innerpeace.innerpeace.utils.ResponseParserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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