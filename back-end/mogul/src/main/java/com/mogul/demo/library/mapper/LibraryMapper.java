package com.mogul.demo.library.mapper;

import com.mogul.demo.library.dto.LibraryResponse;
import com.mogul.demo.library.entity.LibraryThumbnailEntity;
import com.mogul.demo.library.entity.LibraryWebtoonThumbnailEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface LibraryMapper {

    public static LibraryMapper INSTANCE = new LibraryMapperImpl();

    @Mapping(source = "libraryId", target = "id")
    LibraryResponse fromLibraryWebtoonThumbnailEntityToLibraryResponse(LibraryWebtoonThumbnailEntity libraryWebtoonThumbnailEntity);


    LibraryResponse fromLibraryThumbnailEntityToLibraryResponse(LibraryThumbnailEntity libraryThumbnailEntity);
}
