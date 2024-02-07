package com.mogul.demo.common.service;

import com.mogul.demo.common.dto.LibraryDetailResponse;
import com.mogul.demo.review.dto.ReviewAddRequest;
import com.mogul.demo.review.dto.ReviewUpdateRequest;
import com.mogul.demo.review.service.ReviewService;
import com.mogul.demo.user.entity.User;
import com.mogul.demo.user.service.UserService;
import com.mogul.demo.util.CustomResponse;
import com.mogul.demo.webtoon.service.WebtoonService;
import jakarta.persistence.EntityNotFoundException;
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
    @Transactional
    public boolean modifyReview(ReviewUpdateRequest reviewUpdateRequest) {
        User user = userService.getUserFromAuth();
        if(reviewService.findUser(reviewUpdateRequest.getId())!=user.getId()){
            throw new EntityNotFoundException("접근 권한이 없습니다: 해당 사용자가 작성한 리뷰가 아닙니다.");
        }
        return reviewService.modifyReview(reviewUpdateRequest);
    }

    @Override
    @Transactional
    public boolean removeReview(Long id) {
        User user = userService.getUserFromAuth();
        if(reviewService.findUser(id)!=user.getId()){
            throw new EntityNotFoundException("접근 권한이 없습니다: 해당 사용자가 작성한 리뷰가 아닙니다.");
        }
        return reviewService.removeReview(id);
    }
}
