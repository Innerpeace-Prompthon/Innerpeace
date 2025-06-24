package com.innerpeace.innerpeace.calllassapi.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ResponseDto {
    private String text;
    private List<TravelDay> travelSchedule;

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class TravelDay {
        private String day;
        private String date;
        private List<Plan> plan;
    }

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Plan {
        private int order;
        private String place;
        private String description;
        private String activity;
        private String image;
        private double latitude;
        private double longitude;
    }
}
