package com.mogul.demo.library.service;

import com.mogul.demo.library.dto.*;
import com.mogul.demo.library.entity.LibraryEntity;
import com.mogul.demo.library.mapper.LibraryMapper;
import com.mogul.demo.library.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class LibraryServiceImpl implements LibraryService{

    private final LibraryWebtoonThumbnailRepository libraryWebtoonThumbnailRepository;

    private final LibraryThumbnailRepository libraryThumbnailRepository;

    private final LibraryRepository libraryRepository;

    private final LibraryWebtoonRepository libraryWebtoonRepository;

    private final LibrarySubscriptionThumbnailRepository librarySubscriptionThumbnailRepository;

    private final LibraryUserRepository libraryUserRepository;

    @Override
    @Transactional(readOnly = true)
    public List<LibraryResponse> findLibrariesByWebtoonId(Long webtoonId, int pageNumber, int pageSize) {
        return libraryWebtoonThumbnailRepository.findByWebtoonIdAndIsDeletedFalseOrderBySubscriberNumberDesc(webtoonId, PageRequest.of(pageNumber, pageSize))
                .stream().map(LibraryMapper.INSTANCE::fromLibraryWebtoonThumbnailEntityToLibraryResponse).collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<LibraryResponse> findLibrariesHot(int pageNumber, int pageSize) {
        return libraryThumbnailRepository.findByIsDeletedFalse(PageRequest.of(pageNumber, pageSize, Sort.by("subscriberNumber").descending()))
                .stream().map(LibraryMapper.INSTANCE::fromLibraryThumbnailEntityToLibraryResponse).collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<LibraryResponse> findLibrariesByUserId(long userId) {
        return libraryThumbnailRepository.findByUserIdAndIsDeletedFalseOrderByRegisteredDateDesc(userId)
                .stream().map(LibraryMapper.INSTANCE::fromLibraryThumbnailEntityToLibraryResponse).collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public LibraryResponse findLibraryById(Long libraryId) {
        return LibraryMapper.INSTANCE.fromLibraryThumbnailEntityToLibraryResponse(libraryThumbnailRepository.findOneById(libraryId));
    }

    @Override
    @Transactional
    public Long addLibrary(LibraryCreateRequest libraryCreateRequest) {
        LibraryEntity libraryEntity = LibraryMapper.INSTANCE.fromLibraryCreateRequestToLibraryEntity(libraryCreateRequest);
        return libraryRepository.save(libraryEntity).getId();
    }

    @Override
    @Transactional
    public boolean removeLibrary(long id) {
        if(!libraryRepository.existsByIdAndIsDeletedFalse(id)){
            return false;
        }
        libraryRepository.updateIsDeleted(id);
        return true;
    }

    @Override
    @Transactional
    public boolean addWebtoon(LibraryAddWebtoonRequest libraryAddWebtoonRequest) {
        if(!libraryRepository.existsByIdAndIsDeletedFalse(libraryAddWebtoonRequest.getId())){
            return false;
        }else{
            libraryWebtoonRepository.save(LibraryMapper.INSTANCE.fromLibraryAddWebtoonRequestToLibraryWebtoonEntity(libraryAddWebtoonRequest));
            return true;
        }
    }

    @Override
    @Transactional
    public List<SubscriptionResponse> findSubscription(Long userId, int pageNumber, int pageSize) {
        return librarySubscriptionThumbnailRepository.findByUserId(userId, PageRequest.of(pageNumber, pageSize))
                .stream().map(LibraryMapper.INSTANCE::fromLibrarySubscriptionThumbnailEntityToSubscriptionResponse).collect(Collectors.toList());
    }

    @Override
    @Transactional
    public boolean addSubscription(SubcriptionRequest subcriptionRequest) {
        if(!libraryRepository.existsByIdAndIsDeletedFalse(subcriptionRequest.getLibraryId())){
            return false;
        }
        if(libraryUserRepository.existsByLibraryIdAndUserId(subcriptionRequest.getLibraryId(), subcriptionRequest.getUserId())){
            return false;
        }
        subcriptionRequest.setRegisteredDate(new Date());
        libraryUserRepository.save(LibraryMapper.INSTANCE.fromSubscriptionRequestToLibraryUserEntity(subcriptionRequest));
        libraryRepository.updateSubscriberNumberById(subcriptionRequest.getLibraryId());
        return true;
    }

    @Override
    @Transactional
    public boolean removeSubscription(SubscriptionCancelRequest subscriptionCancelRequest) {
        if(!libraryUserRepository.existsByLibraryIdAndUserId(subscriptionCancelRequest.getLibraryId(), subscriptionCancelRequest.getUserId())){
            return false;
        }
        libraryUserRepository.delete(LibraryMapper.INSTANCE.fromSubscriptionCancelRequestToLibraryUserEntity(subscriptionCancelRequest));
        return true;
    }

    @Override
    @Transactional
    public boolean modifyLibrary(LibraryUpdateRequest libraryUpdateRequest) {
        if(!libraryRepository.existsByIdAndIsDeletedFalse(libraryUpdateRequest.getId())){
            return false;
        }
        libraryRepository.updateNameById(libraryUpdateRequest.getId(), libraryUpdateRequest.getName());
        return true;
    }

}
