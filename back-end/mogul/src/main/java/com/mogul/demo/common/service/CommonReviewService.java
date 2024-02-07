package com.mogul.demo.common.service;

import com.mogul.demo.common.dto.LibraryDetailResponse;
import com.mogul.demo.review.dto.ReviewAddRequest;
import com.mogul.demo.util.CustomResponse;

public interface CommonReviewService {
    CustomResponse addReview(Long webtoonId, ReviewAddRequest reviewAddRequest);

}
