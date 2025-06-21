package com.innerpeace.innerpeace.utils;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ResponseParserServiceImpl implements ResponseParserService {

    private final LaaSResponseParser parser = new LaaSResponseParser();

    @Override
    public ParseResponseDto parse(ParseRequestDto request) {
        String cleaned = parser.clean(request.getMessage());
        return ParseResponseDto.builder().message(cleaned).build();
    }
}
