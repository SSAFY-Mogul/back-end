package com.mogul.demo.search.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import com.mogul.demo.search.document.WebtoonDocument;
import com.mogul.demo.search.dto.WebtoonSearchResponse;

@Mapper
public interface WebtoonDocumentMapper {
	WebtoonDocumentMapper INSTANCE = Mappers.getMapper(WebtoonDocumentMapper.class);

	WebtoonSearchResponse webtoonDocuementToWebtoonSearchResponse(WebtoonDocument webtoonDocument);
}
