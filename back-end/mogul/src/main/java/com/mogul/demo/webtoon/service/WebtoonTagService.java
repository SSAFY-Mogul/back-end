package com.mogul.demo.webtoon.service;


import com.mogul.demo.admin.dto.WebtoonAddRequest;
import com.mogul.demo.admin.dto.WebtoonTagAddRequest;
import com.mogul.demo.webtoon.dto.WebtoonResponse;
import com.mogul.demo.webtoon.dto.WebtoonTagResponse;

import java.util.List;

public interface WebtoonTagService {

    List<WebtoonTagResponse> findTag(Long webtoonId);

    List<WebtoonResponse> findWebtoonByTagId(Long tagId);

    Long addTag(WebtoonTagAddRequest webtoonTagAddRequest);

    boolean removeWebtoonTag(Long id);

    List<WebtoonTagResponse> findWebtoonTag();
}
