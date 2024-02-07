package com.mogul.demo.common.service;

import com.mogul.demo.common.dto.LibraryDetailResponse;
import com.mogul.demo.library.dto.LibraryCreateRequest;
import com.mogul.demo.library.service.LibraryService;
import com.mogul.demo.user.entity.User;
import com.mogul.demo.user.service.UserService;
import com.mogul.demo.webtoon.service.WebtoonService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CommonLibraryServiceImpl implements CommonLibraryService{

    private final LibraryService libraryService;

    private final WebtoonService webtoonService;

    private final UserService userService;

    @Override
    public LibraryDetailResponse getLibraryDetail(Long libraryId) {
        LibraryDetailResponse libraryDetailResponse = new LibraryDetailResponse();
        libraryDetailResponse.setLibrary(libraryService.findLibraryById(libraryId));
        libraryDetailResponse.setWebtoons(webtoonService.findWebtoonsByLibraryId(libraryId));
        return libraryDetailResponse;
    }

    @Override
    public Long addLibrary(LibraryCreateRequest libraryCreateRequest) {
        User user = userService.getUserFromAuth();
        libraryCreateRequest.setUserId(user.getId());
        return libraryService.addLibrary(libraryCreateRequest);
    }
}
