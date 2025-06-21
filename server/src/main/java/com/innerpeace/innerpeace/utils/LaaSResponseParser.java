package com.innerpeace.innerpeace.utils;

import java.util.List;

public class LaaSResponseParser {

    public String clean(String message) {
        if (message == null) return "";

        return message
                .replaceAll("[\\*\\#]+", "")                  // 마크다운 기호 제거
                .replaceAll("[🌊🌲🌅🌺🌿🚗📍⏰💰📞😊]+", "") // 이모지 제거
                .replaceAll("\\\\n", " ")                     // \n 제거 (이스케이프된 줄바꿈)
                .replaceAll("\\s{2,}", " ")                   // 중복 공백 제거
                .trim();
    }
}
