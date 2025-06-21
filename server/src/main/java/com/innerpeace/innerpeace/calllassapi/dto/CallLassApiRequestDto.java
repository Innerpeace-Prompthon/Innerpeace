package com.innerpeace.innerpeace.calllassapi.dto;

import lombok.Getter;

@Getter
public class CallLassApiRequestDto {
    private String region;           // 여행 지역
    private String travelType;       // 선호 여행 타입
    private String date;             // 여행 날짜
    private String transportation;   // 이동수단
    private String userInput;        // 자연어 입력
}
