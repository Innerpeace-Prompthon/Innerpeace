package com.innerpeace.innerpeace.calllassapi.controller;

import com.innerpeace.innerpeace.calllassapi.dto.CallLassApiRequestDto;
import com.innerpeace.innerpeace.calllassapi.dto.CallLassApiResponseDto;
import com.innerpeace.innerpeace.calllassapi.service.CallLassApiService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/lass")
public class CallLassApiController {

    private final CallLassApiService callLassApiService;

    @PostMapping("/chat")
    public ResponseEntity<CallLassApiResponseDto> callLassApi(@RequestBody CallLassApiRequestDto requestDto) {

        return ResponseEntity.ok(callLassApiService.requestChatCompletion(requestDto));
    }
}
