package com.mogul.demo.review.mapper;

import com.mogul.demo.review.dto.ReviewAddRequest;
import com.mogul.demo.review.dto.ReviewResponse;
import com.mogul.demo.review.entity.ReviewEntity;
import org.mapstruct.Mapper;

@Mapper
public interface ReviewMapper {

    public static ReviewMapper INSTANCE = new ReviewMapperImpl();

    ReviewResponse fromReviewEntityToReivewResponse(ReviewEntity reviewEntity);

    ReviewEntity fromReviewAddRequestToReviewEntity(ReviewAddRequest reviewAddRequest);
}
