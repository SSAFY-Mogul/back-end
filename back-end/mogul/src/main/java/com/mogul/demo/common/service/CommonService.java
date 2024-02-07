package com.mogul.demo.common.service;

import com.mogul.demo.common.dto.WebtoonMainResponse;

public interface CommonService {
    WebtoonMainResponse listWebtoonMain(int pageNumber, int pageSize);
}
