package com.mogul.demo.library.mapper;

import com.mogul.demo.library.dto.LibraryResponse;
import com.mogul.demo.library.entity.LibraryWebtoonThumbnailEntity;
import org.mapstruct.Mapper;

@Mapper
public interface LibraryMapper {

    public static LibraryMapper INSTANCE = new LibraryMapperImpl();

    LibraryResponse fromLibraryWebtoonThumbnailEntityToLibraryResponse(LibraryWebtoonThumbnailEntity libraryWebtoonThumbnailEntity);

}
