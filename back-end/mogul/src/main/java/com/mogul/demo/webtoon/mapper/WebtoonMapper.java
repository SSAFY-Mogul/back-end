package com.mogul.demo.webtoon.mapper;

import com.mogul.demo.webtoon.dto.WebtoonDetailResponse;
import com.mogul.demo.webtoon.dto.WebtoonResponse;
import com.mogul.demo.webtoon.dto.WebtoonTagResponse;
import com.mogul.demo.webtoon.entity.WebtoonCountEntity;
import com.mogul.demo.webtoon.entity.WebtoonEntity;
import com.mogul.demo.webtoon.entity.WebtoonLibraryEntity;
import com.mogul.demo.webtoon.entity.WebtoonWebtoonTagTagEntity;
import org.mapstruct.*;

@Mapper
public interface WebtoonMapper{

    public static WebtoonMapper INSTANCE = new WebtoonMapperImpl();

    WebtoonResponse fromWebtoonEntityToWebtoonResponse(WebtoonEntity webtoonEntity);

    WebtoonResponse fromWebtoonCountEntityToWebtoonResponse(WebtoonCountEntity webtoonCountEntity);

    WebtoonDetailResponse fromWebtoonEntityToWebtoonDtailResponse(WebtoonEntity webtoonEntity);

    @Mapping(source = "webtoonId", target = "id")
    WebtoonResponse fromWebtoonLibraryEntityToWebtoonResponse(WebtoonLibraryEntity webtoonLibraryEntity);

    WebtoonTagResponse fromWebtoonWebtoonTagTagEntityToWebtoonResponse(WebtoonWebtoonTagTagEntity webtoonWebtoonTagTagEntity);
}
