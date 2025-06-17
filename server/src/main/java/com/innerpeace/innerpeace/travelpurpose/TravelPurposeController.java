package com.innerpeace.innerpeace.travelpurpose;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class TravelPurposeController {
    private final TravelPurposeService travelPurposeService;

    @PostMapping("/api/travels")
    public ResponseEntity<TravelPurposeResponseDto> chat(@RequestBody TravelPurposeRequestDto travelPurposeRequestDto) {
        TravelPurposeResponseDto travelPurposeResponseDto =
                travelPurposeService.requestChatCompletion(travelPurposeRequestDto.getUserInput());
        return new ResponseEntity<>(travelPurposeResponseDto, HttpStatus.OK);
    }

}
