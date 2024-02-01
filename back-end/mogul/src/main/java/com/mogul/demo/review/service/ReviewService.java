package com.mogul.demo.review.service;

import com.mogul.demo.review.dto.ReviewAddRequest;
import com.mogul.demo.review.dto.ReviewResponse;
import com.mogul.demo.review.dto.ReviewUpdateRequest;

import java.util.List;

public interface ReviewService {
    List<ReviewResponse> findReviewsByWebtoonId(Long webtoonId, int pageNumber, int pageSize);

    boolean addReview(ReviewAddRequest reviewAddRequest);

    boolean modifyReview(ReviewUpdateRequest reviewUpdateRequest);

    boolean removeReview(Long id);
}
