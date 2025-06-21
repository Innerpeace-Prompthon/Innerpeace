package com.innerpeace.innerpeace.chat;

import java.util.regex.Pattern;


// LaaS 응답 텍스트 정리 전용 유틸리티
// 이상한 문자, 포맷팅 문제 등을 해결

public class TextCleaner {

    // 이상한 문자들 패턴
    private static final Pattern STRANGE_CHARS = Pattern.compile("[◆◇□■▲△▽▼♦♢\uFFFD\u25A0\u25A1\u25B2\u25B3]");

    // LaaS 변수 패턴
    private static final Pattern LAAS_VARIABLES = Pattern.compile("\\$\\{[^}]+\\}");

    // 과도한 공백 패턴
    private static final Pattern EXCESSIVE_SPACES = Pattern.compile("\\s{2,}");

    // 과도한 줄바꿈 패턴
    private static final Pattern EXCESSIVE_NEWLINES = Pattern.compile("\n{3,}");

    /**
     * LaaS 응답 텍스트를 완전히 정리
     */
    public static String cleanLaaSResponse(String content) {
        if (content == null || content.isEmpty()) {
            return content;
        }

        return content
                // 1단계: 기본 정리
                .trim()

                // 2단계: 이상한 문자 제거
                .replaceAll(STRANGE_CHARS.pattern(), "")

                // 3단계: 처리되지 않은 LaaS 변수 제거
                .replaceAll(LAAS_VARIABLES.pattern(), "")

                // 4단계: 구조화
                .replaceAll("###\\s*([^\\n]+)", "\n\n### $1\n")
                .replaceAll("\\*\\*([^*\\n]+)\\*\\*", "\n\n**$1**\n")

                // 5단계: 이모지 정보 정리
                .replaceAll("([📍🚗⏰💰📞🌿☀️🌤️🌅🏠💡🅿️])\\s*([^\\n]+)", "\n$1 $2")

                // 6단계: 정보 키워드 정리
                .replaceAll("(위치|교통|소요시간|비용|연락처|개요|주소)\\s*:\\s*", "\n$1: ")

                // 7단계: 장소명 패턴 정리
                .replaceAll("\\[([^\\]]+)\\]", "\n\n**$1**\n")

                // 8단계: 문장 구분
                .replaceAll("([.!?])\\s+([가-힣A-Za-z0-9])", "$1\n\n$2")

                // 9단계: 과도한 공백/줄바꿈 정리
                .replaceAll(EXCESSIVE_SPACES.pattern(), " ")
                .replaceAll(EXCESSIVE_NEWLINES.pattern(), "\n\n")

                // 10단계: 최종 정리
                .replaceAll("^\\s+", "")
                .replaceAll("\\s+$", "")
                .trim();
    }

    /**
     * 여행 응답에 특화된 포맷팅
     */
    public static String formatTravelResponse(String content) {
        String cleaned = cleanLaaSResponse(content);

        return cleaned
                // 여행 관련 키워드 강화
                .replaceAll("(추천 장소|일정 구성|맞춤 재추천)", "\n\n### $1\n")
                .replaceAll("(Day \\d+)", "\n\n**$1**\n")

                // 정보 블록 정리
                .replaceAll("(📍[^\\n]*\\n)(🚗[^\\n]*\\n)(⏰[^\\n]*\\n)", "$1$2$3\n")

                // 최종 정리
                .replaceAll("\n{3,}", "\n\n")
                .trim();
    }
}