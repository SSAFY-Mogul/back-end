package com.mogul.demo.common.service;

import com.mogul.demo.common.dto.LibraryDetailResponse;
import com.mogul.demo.library.dto.*;
import com.mogul.demo.library.service.LibraryService;
import com.mogul.demo.user.entity.User;
import com.mogul.demo.user.service.UserService;
import com.mogul.demo.util.CustomResponse;
import com.mogul.demo.webtoon.service.WebtoonService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
        User user = userService.getUserFromAuth();
        libraryCreateRequest.setUserId(user.getId());
        return libraryService.addLibrary(libraryCreateRequest);
    }

    @Override
    @Transactional
    public CustomResponse addWebtoon(Long id, LibraryAddWebtoonRequest libraryAddWebtoonRequest) {
        CustomResponse res;
        User user = userService.getUserFromAuth();
        // 해당 유저가 해당 서재에 대하여권한이 있는지 체크!!!!
        libraryAddWebtoonRequest.setId(id);
        if(webtoonService.isExist(libraryAddWebtoonRequest.getWebtoonId())) {
            boolean data = libraryService.addWebtoon(libraryAddWebtoonRequest);
            res = new CustomResponse<Boolean>(data ? 200 : 404, data, data ? "웹툰 추가 성공" : "웹툰 추가 실패");
        }else{
            res = new CustomResponse(404, null, "존재하지 않는 웹툰");
        }
        return res;
    }

    @Override
    @Transactional
    public boolean addSubscription(SubcriptionRequest subcriptionRequest) {
        User user = userService.getUserFromAuth();
        subcriptionRequest.setUserId(user.getId());
        return libraryService.addSubscription(subcriptionRequest);
    }

    @Override
    @Transactional
    public boolean removeSubscription(SubscriptionCancelRequest subscriptionCancelRequest) {
        User user = userService.getUserFromAuth();
        subscriptionCancelRequest.setUserId(user.getId());
        return libraryService.removeSubscription(subscriptionCancelRequest);
    }

    @Override
    @Transactional
    public boolean modifyLibrary(Long id, LibraryUpdateRequest libraryUpdateRequest) {
        User user = userService.getUserFromAuth();
        // 해당 유저가 해당 서재에 대하여 권한이 있는지 체크!!!
        libraryUpdateRequest.setId(id);
        return libraryService.modifyLibrary(libraryUpdateRequest);
    }

    @Override
    @Transactional(readOnly = true)
    public List findLibrariesByUserId() {
        User user = userService.getUserFromAuth();
        return libraryService.findLibrariesByUserId(user.getId());
    }

    @Override
    @Transactional(readOnly = true)
    public List findSubscription(int pageNumber, int pageSize) {
        User user = userService.getUserFromAuth();
        return libraryService.findSubscription(user.getId(), pageNumber, pageSize);
    }

    @Override
    @Transactional
    public boolean removeLibrary(long id) {
        User user = userService.getUserFromAuth();
        // 유저가 해당 서재에 권한이 있는지 체크
        return libraryService.removeLibrary(id);
    }
}
