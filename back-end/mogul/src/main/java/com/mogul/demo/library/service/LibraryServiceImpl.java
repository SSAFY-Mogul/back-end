package com.mogul.demo.library.service;

import com.mogul.demo.library.dto.LibraryResponse;
import com.mogul.demo.library.mapper.LibraryMapper;
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

    @Override
    public List<LibraryResponse> findLibrariesByWebtoonId(long webtoonId, int pageNumber, int pageSize) {
        return libraryWebtoonThumbnailRepository.findAllByWebtoonIdAndIsDeletedFalseOrderBySubscriberNumberDesc(webtoonId, PageRequest.of(pageNumber, pageSize)).get().stream().map(LibraryMapper.INSTANCE::fromLibraryWebtoonThumbnailEntityToLibraryResponse).collect(Collectors.toList());
    }
}