package com.mogul.demo.webtoon.mapper;

import com.mogul.demo.webtoon.dto.WebtoonDetailResponse;
import com.mogul.demo.webtoon.dto.WebtoonDto;
import com.mogul.demo.webtoon.dto.WebtoonResponse;
import com.mogul.demo.webtoon.entity.WebtoonCountEntity;
import com.mogul.demo.webtoon.entity.WebtoonEntity;
import org.mapstruct.*;

@Mapper
public interface WebtoonMapper{

    public static WebtoonMapper INSTANCE = new WebtoonMapperImpl();

    WebtoonResponse fromWebtoonEntityToWebtoonResponse(WebtoonEntity webtoonEntity);

    WebtoonResponse fromWebtoonCountEntityToWebtoonResponse(WebtoonCountEntity webtoonCountEntity);

    WebtoonDetailResponse fromWebtoonEntityToWebtoonDtailResponse(WebtoonEntity webtoonEntity);
}
