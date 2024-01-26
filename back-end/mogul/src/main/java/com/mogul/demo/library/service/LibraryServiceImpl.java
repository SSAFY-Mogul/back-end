package com.mogul.demo.library.service;

import com.mogul.demo.library.dto.LibraryCreateRequest;
import com.mogul.demo.library.dto.LibraryResponse;
import com.mogul.demo.library.entity.LibraryEntity;
import com.mogul.demo.library.mapper.LibraryMapper;
import com.mogul.demo.library.repository.LibraryRepository;
import com.mogul.demo.library.repository.LibraryThumbnailRepository;
import com.mogul.demo.library.repository.LibraryWebtoonThumbnailRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class LibraryServiceImpl implements LibraryService{

    @Autowired
    LibraryWebtoonThumbnailRepository libraryWebtoonThumbnailRepository;

    @Autowired
    LibraryThumbnailRepository libraryThumbnailRepository;

    @Autowired
    LibraryRepository libraryRepository;

    @Override
    public List<LibraryResponse> findLibrariesByWebtoonId(long webtoonId, int pageNumber, int pageSize) {
        return libraryWebtoonThumbnailRepository.findByWebtoonIdAndIsDeletedFalseOrderBySubscriberNumberDesc(webtoonId, PageRequest.of(pageNumber, pageSize))
                .stream().map(LibraryMapper.INSTANCE::fromLibraryWebtoonThumbnailEntityToLibraryResponse).collect(Collectors.toList());
    }

    @Override
    public List<LibraryResponse> findLibrariesHot(int pageNumber, int pageSize) {
        return libraryThumbnailRepository.findByIsDeletedFalse(PageRequest.of(pageNumber, pageSize, Sort.by("subscriberNumber").descending()))
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

    @Override
    public Long addLibrary(LibraryCreateRequest libraryCreateRequest) {
        LibraryEntity libraryEntity = LibraryMapper.INSTANCE.fromLibraryCreateRequestToLibraryEntity(libraryCreateRequest);
        return libraryRepository.save(libraryEntity).getId();
    }

    @Override
    public boolean removeLibrary(long id) {
        try {
            libraryRepository.deleteById(id);
        }catch (EmptyResultDataAccessException e){
            e.printStackTrace();
            return false;
        }
        return true;
    }
}
