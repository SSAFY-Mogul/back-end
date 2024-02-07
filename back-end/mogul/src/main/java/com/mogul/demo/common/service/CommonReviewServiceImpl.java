package com.mogul.demo.common.service;

import com.mogul.demo.common.dto.LibraryDetailResponse;
import com.mogul.demo.review.dto.ReviewAddRequest;
import com.mogul.demo.review.dto.ReviewUpdateRequest;
import com.mogul.demo.review.service.ReviewService;
import com.mogul.demo.user.entity.User;
import com.mogul.demo.user.service.UserService;
import com.mogul.demo.util.CustomResponse;
import com.mogul.demo.webtoon.service.WebtoonService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CommonReviewServiceImpl implements CommonReviewService{

    private final WebtoonService webtoonService;

    private final UserService userService;

    private final ReviewService reviewService;
    @Override
    @Transactional
    public CustomResponse addReview(Long webtoonId, ReviewAddRequest reviewAddRequest) {
        CustomResponse res;
        if(webtoonService.isExist(webtoonId)) {
            User user = userService.getUserFromAuth();
            Long userId = user.getId();
            reviewAddRequest.setUserId(userId);
            reviewAddRequest.setWebtoonId(webtoonId);
            boolean data = reviewService.addReview(reviewAddRequest);
            res = new CustomResponse<Boolean>(data?200:400, data, data?"리뷰 등록 성공":"리뷰 등록 실패");
        }else{
            res = new CustomResponse(404, null, "존재하지 않는 웹툰입니다.");
        }
        return res;
    }

    @Override
    public boolean modifyReview(ReviewUpdateRequest reviewUpdateRequest) {
        User user = userService.getUserFromAuth();
        // 해당 사용자가 리뷰에 대하여 접근 가능하지 체크!!!
        return reviewService.modifyReview(reviewUpdateRequest);
    }

    @Override
    public boolean removeReview(Long id) {
        User user = userService.getUserFromAuth();
        // 해당 사용자가 해당 리뷰를 지울 수 있는지 체크!!!!
        return reviewService.removeReview(id);
    }
}
