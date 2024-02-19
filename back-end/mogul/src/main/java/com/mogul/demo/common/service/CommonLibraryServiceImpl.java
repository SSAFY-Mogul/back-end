package com.mogul.demo.common.service;

import com.mogul.demo.common.dto.LibraryDetailResponse;
import com.mogul.demo.library.dto.*;
import com.mogul.demo.library.service.LibraryService;
import com.mogul.demo.util.CustomResponse;
import com.mogul.demo.webtoon.service.WebtoonService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.mogul.demo.user.service.*;
import com.mogul.demo.user.entity.*;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CommonLibraryServiceImpl implements CommonLibraryService{

    private final LibraryService libraryService;

    private final WebtoonService webtoonService;

    private final UserService userService;

    @Override
    @Transactional(readOnly = true)
    public LibraryDetailResponse getLibraryDetail(Long libraryId) {
        LibraryDetailResponse libraryDetailResponse = new LibraryDetailResponse();
        libraryDetailResponse.setLibrary(libraryService.findLibraryById(libraryId));
        libraryDetailResponse.setWebtoons(webtoonService.findWebtoonsByLibraryId(libraryId));
        return libraryDetailResponse;
    }

    @Override
    @Transactional
    public Long addLibrary(LibraryCreateRequest libraryCreateRequest) {
        User user = userService.getUserByToken();
        libraryCreateRequest.setUserId(user.getId());
        return libraryService.addLibrary(libraryCreateRequest);
    }

    @Override
    @Transactional
    public CustomResponse addWebtoon(Long id, LibraryAddWebtoonRequest libraryAddWebtoonRequest) {
        CustomResponse res;
        User user = userService.getUserByToken();
        if(user.getId()!=libraryService.findUser(id)){
            throw new EntityNotFoundException("접근 권한 없음:해당 사용자의 서재가 아닙니다.");
        }
        libraryAddWebtoonRequest.setId(id);
        if(webtoonService.isExist(libraryAddWebtoonRequest.getWebtoonId())) {
            boolean data = libraryService.addWebtoon(libraryAddWebtoonRequest);
            res = new CustomResponse<Boolean>(data ? 200 : 404, data, data ? "웹툰 추가 성공" : "웹툰 추가 실패");
        }else{
            throw new EntityNotFoundException("존재하지 않는 웹툰 입니다.");
        }
        return res;
    }

    @Override
    @Transactional
    public boolean addSubscription(SubcriptionRequest subcriptionRequest) {
        User user = userService.getUserByToken();
        subcriptionRequest.setUserId(user.getId());
        return libraryService.addSubscription(subcriptionRequest);
    }

    @Override
    @Transactional
    public boolean removeSubscription(SubscriptionCancelRequest subscriptionCancelRequest) {
        User user = userService.getUserByToken();
        subscriptionCancelRequest.setUserId(user.getId());
        return libraryService.removeSubscription(subscriptionCancelRequest);
    }

    @Override
    @Transactional
    public boolean modifyLibrary(Long id, LibraryUpdateRequest libraryUpdateRequest) {
        User user = userService.getUserByToken();
        if(user.getId()!=libraryService.findUser(id)){
            throw new EntityNotFoundException("접근 권한 없음:해당 사용자의 서재가 아닙니다.");
        }
        libraryUpdateRequest.setId(id);
        return libraryService.modifyLibrary(libraryUpdateRequest);
    }

    @Override
    @Transactional(readOnly = true)
    public List findLibrariesByUserId() {
        User user = userService.getUserByToken();
        return libraryService.findLibrariesByUserId(user.getId());
    }

    @Override
    @Transactional(readOnly = true)
    public List findSubscription(int pageNumber, int pageSize) {
        User user = userService.getUserByToken();
        return libraryService.findSubscription(user.getId(), pageNumber, pageSize);
    }

    @Override
    @Transactional
    public boolean removeLibrary(Long id) {
        User user = userService.getUserByToken();
        if(user.getId()!=libraryService.findUser(id)){
            throw new EntityNotFoundException("접근 권한 없음:사용자의 서재가 아닙니다.");
        }
        return libraryService.removeLibrary(id);
    }

    @Override
    @Transactional
    public boolean removeWebtoon(Long id, Long webtoonId) {
        User user = userService.getUserByToken();
        if(user.getId()!=libraryService.findUser(id)){
            throw new EntityNotFoundException("접근 권한 없음:사용자의 서재가 아닙니다.");
        }
        if(!webtoonService.isExist(webtoonId)){
            throw new EntityNotFoundException("존재하지 않는 웹툰입니다.");
        }
        return libraryService.removeWebtoon(id, webtoonId);
    }
}
