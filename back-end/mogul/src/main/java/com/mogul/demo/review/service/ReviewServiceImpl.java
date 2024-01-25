package com.mogul.demo.review.service;

import com.mogul.demo.review.dto.ReviewResponse;
import com.mogul.demo.review.mapper.ReviewMapper;
import com.mogul.demo.review.repository.ReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ReviewServiceImpl implements ReviewService {

    @Autowired
    ReviewRepository reviewRepository;

    @Override
    public List<ReviewResponse> findReviewsByWebtoonId(long webtoonId, int pageNumber, int pageSize) {
        return reviewRepository.findByWebtoonIdAndIsDeletedFalseOrderByRegisteredDateDesc(webtoonId, PageRequest.of(pageNumber, pageSize)).get().stream().map(ReviewMapper.INSTANCE::fromReviewEntityToReivewResponse).collect(Collectors.toList());
    }
}
