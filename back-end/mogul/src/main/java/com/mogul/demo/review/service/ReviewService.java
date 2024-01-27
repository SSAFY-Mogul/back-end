package com.mogul.demo.review.service;

import com.mogul.demo.review.dto.ReviewAddRequest;
import com.mogul.demo.review.dto.ReviewResponse;

import java.util.List;

public interface ReviewService {
    List<ReviewResponse> findReviewsByWebtoonId(long webtoonId, int pageNumber, int pageSize);

    boolean addReview(ReviewAddRequest reviewAddRequest);
}
