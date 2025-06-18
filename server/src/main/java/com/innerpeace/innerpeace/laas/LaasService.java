package com.innerpeace.innerpeace.laas;

/**
 * LaaS API 연동 서비스 인터페이스
 */
public interface LaasService {

    /**
     * LaaS 프리셋을 호출하여 AI 응답 생성
     *
     * @param userMessage 사용자 입력 메시지
     * @return LaaS에서 생성된 응답
     * @throws Exception LaaS API 호출 실패 시
     */
    String callLaaSPreset(String userMessage) throws Exception;
}