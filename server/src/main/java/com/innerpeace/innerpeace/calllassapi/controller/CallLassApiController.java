package com.innerpeace.innerpeace.calllassapi.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.innerpeace.innerpeace.calllassapi.dto.CallLassApiRequestDto;
import com.innerpeace.innerpeace.calllassapi.dto.ResponseDto;
import com.innerpeace.innerpeace.calllassapi.service.CallLassApiService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/laas")
public class CallLassApiController  {

    private final CallLassApiService callLassApiService;

    @PostMapping("/chat")
    public ResponseEntity<ResponseDto> callLassApi(@RequestBody CallLassApiRequestDto requestDto) throws JsonProcessingException {

        return ResponseEntity.ok(callLassApiService.requestChatCompletion(requestDto));
    }
}