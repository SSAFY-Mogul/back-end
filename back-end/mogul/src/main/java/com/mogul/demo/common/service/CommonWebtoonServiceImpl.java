package com.mogul.demo.common.service;

import com.mogul.demo.common.dto.WebtoonDetailCommonResponse;
import com.mogul.demo.common.dto.WebtoonMainResponse;
import com.mogul.demo.library.service.LibraryService;
import com.mogul.demo.review.service.ReviewService;
import com.mogul.demo.user.entity.User;
import com.mogul.demo.user.service.UserService;
import com.mogul.demo.webtoon.dto.WebtoonLikeResponse;
import com.mogul.demo.webtoon.service.WebtoonLikeService;
import com.mogul.demo.webtoon.service.WebtoonService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CommonWebtoonServiceImpl implements CommonWebtoonService {

    private final WebtoonService webtoonService;

    private final LibraryService libraryService;

    private final ReviewService reviewService;

    private final UserService userService;

    private final WebtoonLikeService webtoonLikeService;

    @Override
    @Transactional(readOnly = true)
    public WebtoonMainResponse listWebtoonMain(int pageNumber, int pageSize) {
        WebtoonMainResponse webtoonMainResponse = new WebtoonMainResponse();
        webtoonMainResponse.setWebtoonTopGrade(webtoonService.findWebtoonOrderByGrade(pageNumber, pageSize));
        webtoonMainResponse.setWebtoonTopLibrary(webtoonService.findWebtoonOrderByLibraryCount(pageNumber, pageSize));
        return webtoonMainResponse;
    }

    @Override
    @Transactional(readOnly = true)
    public WebtoonDetailCommonResponse getWebtoonDetail(Long webtoonId, int pageNumber, int pageSize) {
        WebtoonDetailCommonResponse webtoonDetailCommonResponse = new WebtoonDetailCommonResponse();
        webtoonDetailCommonResponse.setWebtoonDetail(webtoonService.findWebtoonById(webtoonId));
        webtoonDetailCommonResponse.setReviews(reviewService.findReviewsByWebtoonId(webtoonId, pageNumber, pageSize));
        webtoonDetailCommonResponse.setLibraries(libraryService.findLibrariesByWebtoonId(webtoonId, pageNumber, pageSize));
        return webtoonDetailCommonResponse;
    }

    @Override
    @Transactional(readOnly = true)
    public WebtoonLikeResponse getLike(Long webtoonId) {
        User user = userService.getUserFromAuth();
        return webtoonLikeService.getLike(webtoonId, user.getId());
    }

    @Override
    @Transactional
    public boolean addLike(Long webtoonId) {
        User user = userService.getUserFromAuth();
        return webtoonLikeService.addLike(webtoonId, user.getId());
    }

    @Override
    @Transactional
    public boolean removeLike(Long webtoonId) {
        User user = userService.getUserFromAuth();
        return webtoonLikeService.removeLike(webtoonId, user.getId());
    }
}
