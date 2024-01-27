package com.mogul.demo.library.mapper;

import com.mogul.demo.library.dto.*;
import com.mogul.demo.library.entity.*;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface LibraryMapper {

    public static LibraryMapper INSTANCE = new LibraryMapperImpl();

    @Mapping(source = "libraryId", target = "id")
    LibraryResponse fromLibraryWebtoonThumbnailEntityToLibraryResponse(LibraryWebtoonThumbnailEntity libraryWebtoonThumbnailEntity);


    LibraryResponse fromLibraryThumbnailEntityToLibraryResponse(LibraryThumbnailEntity libraryThumbnailEntity);

    LibraryEntity fromLibraryCreateRequestToLibraryEntity(LibraryCreateRequest libraryCreateRequest);

    @Mapping(source = "id", target = "libraryId")
    LibraryWebtoonEntity fromLibraryAddWebtoonRequestToLibraryWebtoonEntity(LibraryAddWebtoonRequest libraryAddWebtoonRequest);

    SubscriptionResponse fromLibrarySubscriptionThumbnailEntityToSubscriptionResponse(LibrarySubscriptionThumbnailEntity librarySubscriptionThumbnailEntity);

    LibraryUserEntity fromSubscriptionRequestToLibraryUserEntity(SubcriptionRequest subcriptionRequest);
}
