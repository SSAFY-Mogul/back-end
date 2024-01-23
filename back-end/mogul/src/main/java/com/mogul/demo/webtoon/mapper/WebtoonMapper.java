package com.mogul.demo.webtoon.mapper;

import com.mogul.demo.webtoon.dto.WebtoonDto;
import com.mogul.demo.webtoon.entity.WebtoonCountEntity;
import com.mogul.demo.webtoon.entity.WebtoonEntity;
import org.mapstruct.*;

@Mapper
public interface WebtoonMapper{

    WebtoonDto fromWebtoonEntityToWebtoonDto(WebtoonEntity webtoonEntity);

    WebtoonDto fromWebtoonCountEntityToWebtoonDto(WebtoonCountEntity webtoonCountEntity);

    Object fromWebtoonEntityToWebtoonDtailDto(WebtoonEntity webtoonEntity);
}
