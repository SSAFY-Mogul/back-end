package com.mogul.demo.library.service;

import com.mogul.demo.library.dto.*;
import com.mogul.demo.library.entity.LibraryEntity;
import com.mogul.demo.library.entity.LibraryThumbnailEntity;
import com.mogul.demo.library.entity.LibraryWebtoonEntity;
import com.mogul.demo.library.entity.LibraryWebtoonPK;
import com.mogul.demo.library.mapper.LibraryMapper;
import com.mogul.demo.library.repository.*;
import com.mogul.demo.user.entity.User;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Optional;
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
        List data =  libraryWebtoonThumbnailRepository.findByWebtoonIdAndIsDeletedFalseOrderBySubscriberNumberDesc(webtoonId, PageRequest.of(pageNumber, pageSize))
                .stream().map(LibraryMapper.INSTANCE::fromLibraryWebtoonThumbnailEntityToLibraryResponse).collect(Collectors.toList());
        return data;
    }

    @Override
    @Transactional(readOnly = true)
    public List<LibraryResponse> findLibrariesHot(int pageNumber, int pageSize) {
        List data =  libraryThumbnailRepository.findByIsDeletedFalse(PageRequest.of(pageNumber, pageSize, Sort.by("subscriberNumber").descending()))
                .stream().map(LibraryMapper.INSTANCE::fromLibraryThumbnailEntityToLibraryResponse).collect(Collectors.toList());
        if(data.isEmpty()){
            throw new EntityNotFoundException("인기 서재가 없습니다.");
        }
        return data;
    }

    @Override
    @Transactional(readOnly = true)
    public List<LibraryResponse> findLibrariesByUserId(long userId) {
        List data = libraryThumbnailRepository.findByUserIdAndIsDeletedFalseOrderByRegisteredDateDesc(userId)
                .stream().map(LibraryMapper.INSTANCE::fromLibraryThumbnailEntityToLibraryResponse).collect(Collectors.toList());
        if(data.isEmpty()){
            throw new EntityNotFoundException("해당 유저는 서재를 갖고 있지 않습니다.");
        }
        return data;
    }

    @Override
    @Transactional(readOnly = true)
    public LibraryResponse findLibraryById(Long libraryId) {
        Optional<LibraryThumbnailEntity> data = libraryThumbnailRepository.findOneById(libraryId);
        if(data.isEmpty()){
            throw new EntityNotFoundException("해당 아이디의 서재가 존재하지 않습니다.");
        }
        return LibraryMapper.INSTANCE.fromLibraryThumbnailEntityToLibraryResponse(data.get());
    }

    @Override
    @Transactional
    public Long addLibrary(LibraryCreateRequest libraryCreateRequest) {
        if(libraryRepository.countByUserIdAndIsDeletedFalse(libraryCreateRequest.getUserId())>=10){
            throw new EntityNotFoundException("해당 유저는 10개 이상의 서재를 이미 갖고 있습니다.");
        }
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
            throw new EntityNotFoundException("해당 서재를 찾을 수 없습니다.");
        }else{
            if(libraryWebtoonRepository.countById(libraryAddWebtoonRequest.getId())>=20){
                throw new EntityNotFoundException("해당 서재에는 이미 20개 이상의 웹툰이 추가되어 있습니다.");
            }
            libraryWebtoonRepository.save(LibraryMapper.INSTANCE.fromLibraryAddWebtoonRequestToLibraryWebtoonEntity(libraryAddWebtoonRequest));
            return true;
        }
    }

    @Override
    @Transactional
    public List<SubscriptionResponse> findSubscription(Long userId, int pageNumber, int pageSize) {
        List data =  librarySubscriptionThumbnailRepository.findByUserId(userId, PageRequest.of(pageNumber, pageSize))
                .stream().map(LibraryMapper.INSTANCE::fromLibrarySubscriptionThumbnailEntityToSubscriptionResponse).collect(Collectors.toList());
        if(data.isEmpty()){
            throw new EntityNotFoundException("해당 유저가 구독한 서재가 없습니다.");
        }
        return data;
    }

    @Override
    @Transactional
    public boolean addSubscription(SubcriptionRequest subcriptionRequest) {
        if(!libraryRepository.existsByIdAndIsDeletedFalse(subcriptionRequest.getLibraryId())){
            return false;
        }
        if(libraryRepository.findById(subcriptionRequest.getLibraryId()).get().getUserId()==subcriptionRequest.getUserId()){
            throw new EntityNotFoundException("자신의 서재는 구독할 수 없습니다.");
        }
        if(libraryUserRepository.existsByLibraryIdAndUserId(subcriptionRequest.getLibraryId(), subcriptionRequest.getUserId())){
            return false;
        }
        libraryUserRepository.save(LibraryMapper.INSTANCE.fromSubscriptionRequestToLibraryUserEntity(subcriptionRequest));
        return true;
    }

    @Override
    @Transactional
    public boolean removeSubscription(SubscriptionCancelRequest subscriptionCancelRequest) {
        if(!libraryUserRepository.existsByLibraryIdAndUserId(subscriptionCancelRequest.getLibraryId(), subscriptionCancelRequest.getUserId())){
            throw new EntityNotFoundException("해당 사용자는 해당 서재를 구독한 적이 없습니다.");
        }
        libraryUserRepository.delete(LibraryMapper.INSTANCE.fromSubscriptionCancelRequestToLibraryUserEntity(subscriptionCancelRequest));
        return true;
    }

    @Override
    @Transactional
    public boolean modifyLibrary(LibraryUpdateRequest libraryUpdateRequest) {
        if(!libraryRepository.existsByIdAndIsDeletedFalse(libraryUpdateRequest.getId())){
            throw new EntityNotFoundException("해당 아이디의 서재를 찾을 수 없습니다.");
        }
        libraryRepository.updateNameById(libraryUpdateRequest.getId(), libraryUpdateRequest.getName());
        return true;
    }

    @Override
    @Transactional(readOnly = true)
    public Long findUser(Long id) {
        Optional<LibraryEntity> data = libraryRepository.findById(id);
        if(data.isEmpty()){
            throw new EntityNotFoundException("존재하지 않는 서재");
        }
        return data.get().getUserId();
    }

    @Override
    @Transactional
    public boolean removeWebtoon(Long id, Long webtoonId) {
        if(!libraryWebtoonRepository.existsById(new LibraryWebtoonPK(id, webtoonId))){
            throw new EntityNotFoundException("서재에 추가된 적 없는 웹툰 입니다.");
        }
        libraryWebtoonRepository.delete(new LibraryWebtoonEntity(id, webtoonId));
        return true;
    }

}
