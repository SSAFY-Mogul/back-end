package com.mogul.demo.review.service;

import com.mogul.demo.review.dto.ReviewAddRequest;
import com.mogul.demo.review.dto.ReviewResponse;
import com.mogul.demo.review.dto.ReviewUpdateRequest;

import java.util.List;

public interface ReviewService {
    List<ReviewResponse> findReviewsByWebtoonId(long webtoonId, int pageNumber, int pageSize);

    boolean addReview(ReviewAddRequest reviewAddRequest);

    float findDrawingGrade(long webtoonId);

    float findStoryGrade(long webtoonId);

    float findDirectingGrade(long webtoonId);

    boolean modifyReview(ReviewUpdateRequest reviewUpdateRequest);

    long findWebtoonId(long id);

    boolean removeReview(long id);
}
