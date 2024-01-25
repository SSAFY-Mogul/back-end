package com.mogul.demo.library.service;

import com.mogul.demo.library.dto.LibraryResponse;
import com.mogul.demo.library.entity.LibraryThumbnailEntity;
import com.mogul.demo.library.mapper.LibraryMapper;
import com.mogul.demo.library.repository.LibraryThumbnailRepository;
import com.mogul.demo.library.repository.LibraryWebtoonThumbnailRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class LibraryServiceImpl implements LibraryService{

    @Autowired
    LibraryWebtoonThumbnailRepository libraryWebtoonThumbnailRepository;

    @Autowired
    LibraryThumbnailRepository libraryThumbnailRepository;

    @Override
    public List<LibraryResponse> findLibrariesByWebtoonId(long webtoonId, int pageNumber, int pageSize) {
        return libraryWebtoonThumbnailRepository.findByWebtoonIdAndIsDeletedFalseOrderBySubscriberNumberDesc(webtoonId, PageRequest.of(pageNumber, pageSize))
                .stream().map(LibraryMapper.INSTANCE::fromLibraryWebtoonThumbnailEntityToLibraryResponse).collect(Collectors.toList());
    }

    @Override
    public List<LibraryResponse> findLibrariesHot(int pageNumber, int pageSize) {
        return libraryThumbnailRepository.findByIsDeletedFalseOrderBySubscriberNumber(PageRequest.of(pageNumber, pageSize))
                .stream().map(LibraryMapper.INSTANCE::fromLibraryThumbnailEntityToLibraryResponse).collect(Collectors.toList());
    }

    @Override
    public List<LibraryResponse> findLibrariesByUserId(long userId) {
        return libraryThumbnailRepository.findByUserIdAndIsDeletedFalseOrderByRegisteredDateDesc(userId)
                .stream().map(LibraryMapper.INSTANCE::fromLibraryThumbnailEntityToLibraryResponse).collect(Collectors.toList());
    }

    @Override
    public LibraryResponse findLibraryById(long libraryId) {
        return LibraryMapper.INSTANCE.fromLibraryThumbnailEntityToLibraryResponse(libraryThumbnailRepository.findOneById(libraryId));
    }
}
