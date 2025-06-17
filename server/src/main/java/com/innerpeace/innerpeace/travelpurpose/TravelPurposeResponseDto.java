package com.innerpeace.innerpeace.travelpurpose;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@Getter
public class TravelPurposeResponseDto {
    private final String travelPurpose;
    private final String preferredRegion;
    private final String travelDuration;
    private final String travelDate;
    private final String companionType;
    private final String preferredKeywords;
    private final String additionalRequests;

    @JsonCreator
    public TravelPurposeResponseDto(
            @JsonProperty("여행 목적") String travelPurpose,
            @JsonProperty("선호 지역") String preferredRegion,
            @JsonProperty("여행 기간") String travelDuration,
            @JsonProperty("여행 날짜") String travelDate,
            @JsonProperty("동반자 유형") String companionType,
            @JsonProperty("선호 분위기 키워드") String preferredKeywords,
            @JsonProperty("기타 요구 사항") String additionalRequests) {
        this.travelPurpose = travelPurpose;
        this.preferredRegion = preferredRegion;
        this.travelDuration = travelDuration;
        this.travelDate = travelDate;
        this.companionType = companionType;
        this.preferredKeywords = preferredKeywords;
        this.additionalRequests = additionalRequests;
    }
}
