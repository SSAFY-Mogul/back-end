package com.mogul.demo.common.service;

import com.mogul.demo.common.dto.LibraryDetailResponse;
import com.mogul.demo.review.dto.ReviewAddRequest;
import com.mogul.demo.review.dto.ReviewResponse;
import com.mogul.demo.review.dto.ReviewUpdateRequest;
import com.mogul.demo.util.CustomResponse;

import java.util.List;

public interface CommonReviewService {
    CustomResponse addReview(Long webtoonId, ReviewAddRequest reviewAddRequest);

    boolean modifyReview(ReviewUpdateRequest reviewUpdateRequest);

    boolean removeReview(Long id);

    List<ReviewResponse> findReviewMy(int pageNumber, int pageSize);
}
