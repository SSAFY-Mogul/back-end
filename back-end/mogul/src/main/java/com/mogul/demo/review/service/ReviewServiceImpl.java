package com.mogul.demo.review.service;

import com.mogul.demo.review.dto.ReviewAddRequest;
import com.mogul.demo.review.dto.ReviewResponse;
import com.mogul.demo.review.mapper.ReviewMapper;
import com.mogul.demo.review.repository.ReviewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ReviewServiceImpl implements ReviewService {

    private final ReviewRepository reviewRepository;

    @Override
    public List<ReviewResponse> findReviewsByWebtoonId(long webtoonId, int pageNumber, int pageSize) {
        return reviewRepository.findByWebtoonIdAndIsDeletedFalseOrderByRegisteredDateDesc(webtoonId, PageRequest.of(pageNumber, pageSize))
                .stream().map(ReviewMapper.INSTANCE::fromReviewEntityToReivewResponse).collect(Collectors.toList());
    }

    @Override
    public boolean addReview(ReviewAddRequest reviewAddRequest) {
        reviewAddRequest.setRegisteredDate(new Date());
        reviewRepository.save(ReviewMapper.INSTANCE.fromReviewAddRequestToReviewEntity(reviewAddRequest));
        return true;
    }
}
