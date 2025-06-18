package com.innerpeace.innerpeace.utils;

import com.innerpeace.innerpeace.utils.ParseRequestDto;
import com.innerpeace.innerpeace.utils.ParseResponseDto;

public interface ResponseParserService {
    ParseResponseDto parse(ParseRequestDto request);
}

