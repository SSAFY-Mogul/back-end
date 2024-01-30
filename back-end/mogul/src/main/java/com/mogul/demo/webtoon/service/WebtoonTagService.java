package com.mogul.demo.webtoon.service;


import com.mogul.demo.webtoon.dto.WebtoonResponse;
import com.mogul.demo.webtoon.dto.WebtoonTagResponse;

import java.util.List;

public interface WebtoonTagService {

    List<WebtoonTagResponse> findTag(long webtoonId);

    List<WebtoonResponse> findWebtoonByTagId(long tagId);
}
