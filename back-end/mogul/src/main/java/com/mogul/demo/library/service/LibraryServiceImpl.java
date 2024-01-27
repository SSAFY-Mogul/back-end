package com.mogul.demo.library.service;

import com.mogul.demo.library.dto.LibraryAddWebtoonRequest;
import com.mogul.demo.library.dto.LibraryCreateRequest;
import com.mogul.demo.library.dto.LibraryResponse;
import com.mogul.demo.library.dto.SubscriptionResponse;
import com.mogul.demo.library.entity.LibraryEntity;
import com.mogul.demo.library.entity.LibrarySubscriptionThumbnailPK;
import com.mogul.demo.library.mapper.LibraryMapper;
import com.mogul.demo.library.repository.*;
import com.mogul.demo.webtoon.repository.WebtoonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Date;
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

    @Autowired
    LibraryWebtoonRepository libraryWebtoonRepository;

    @Autowired
    LibrarySubscriptionThumbnailRepository librarySubscriptionThumbnailRepository;

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
        if(!libraryRepository.existsByIdAndIsDeletedFalse(id)){
            return false;
        }
        libraryRepository.update(id);
        return true;
    }

    @Override
    public boolean addWebtoon(LibraryAddWebtoonRequest libraryAddWebtoonRequest) {
        if(!libraryRepository.existsByIdAndIsDeletedFalse(libraryAddWebtoonRequest.getId())){
            return false;
        }else{
            libraryWebtoonRepository.save(LibraryMapper.INSTANCE.fromLibraryAddWebtoonRequestToLibraryWebtoonEntity(libraryAddWebtoonRequest));
            return true;
        }
    }

    @Override
    public List<SubscriptionResponse> findSubscription(long userId, int pageNumber, int pageSize) {
        return librarySubscriptionThumbnailRepository.findByUserId(userId, PageRequest.of(pageNumber, pageSize))
                .stream().map(LibraryMapper.INSTANCE::fromLibrarySubsciptionThumbnailEntityToSubscriptionResponse).collect(Collectors.toList());
    }

}
