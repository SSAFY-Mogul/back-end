package com.mogul.demo.common.service;

import com.mogul.demo.common.dto.WebtoonMainResponse;
import com.mogul.demo.webtoon.service.WebtoonService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CommonServiceImpl implements CommonService{

    private final WebtoonService webtoonService;

    @Override
    public WebtoonMainResponse listWebtoonMain(int pageNumber, int pageSize) {
        WebtoonMainResponse webtoonMainResponse = new WebtoonMainResponse();
        webtoonMainResponse.setWebtoonTopGrade(webtoonService.findWebtoonOrderByGrade(pageNumber, pageSize));
        webtoonMainResponse.setWebtoonTopLibrary(webtoonService.findWebtoonOrderByLibraryCount(pageNumber, pageSize));
        return webtoonMainResponse;
    }
}
