package com.mogul.demo.webtoon.mapper;

import com.mogul.demo.admin.dto.WebtoonAddRequest;
import com.mogul.demo.admin.dto.WebtoonTagAddRequest;
import com.mogul.demo.admin.dto.WebtoonUpdateRequest;
import com.mogul.demo.webtoon.dto.WebtoonDetailResponse;
import com.mogul.demo.webtoon.dto.WebtoonResponse;
import com.mogul.demo.webtoon.dto.WebtoonTagResponse;
import com.mogul.demo.webtoon.entity.*;
import org.mapstruct.*;

@Mapper
public interface WebtoonMapper{

    public static WebtoonMapper INSTANCE = new WebtoonMapperImpl();

    WebtoonResponse fromWebtoonEntityToWebtoonResponse(WebtoonEntity webtoonEntity);

    WebtoonResponse fromWebtoonCountEntityToWebtoonResponse(WebtoonCountEntity webtoonCountEntity);

    WebtoonDetailResponse fromWebtoonEntityToWebtoonDetailResponse(WebtoonEntity webtoonEntity);

    @Mapping(source = "webtoonId", target = "id")
    WebtoonResponse fromWebtoonLibraryEntityToWebtoonResponse(WebtoonLibraryEntity webtoonLibraryEntity);

    WebtoonTagResponse fromWebtoonWebtoonTagTagEntityToWebtoonTagResponse(WebtoonWebtoonTagTagEntity webtoonWebtoonTagTagEntity);

    WebtoonResponse fromWebtoonTagWebtoonEntityToWebtoonResponse(WebtoonTagWebtoonEntity webtoonTagWebtoonEntity);

    WebtoonTagEntity fromWebtoonTagAddRequestToWebtoonTagEntity(WebtoonTagAddRequest webtoonTagAddRequest);

    @Mapping(source = "id", target = "tagId")
    WebtoonTagResponse fromWEbtoonTagEntityToWebtoonTagResponse(WebtoonTagEntity webtoonTagEntity);

    WebtoonEntity fromWEbtoonAddREquestToWebtoonEntity(WebtoonAddRequest webtoonAddRequest);

    WebtoonEntity fromWebtoonUpdateRequestToWebtoonEntity(WebtoonUpdateRequest webtoonUpdateRequest);
}
