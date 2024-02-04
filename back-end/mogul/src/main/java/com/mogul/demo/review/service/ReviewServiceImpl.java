package com.mogul.demo.review.service;

import com.mogul.demo.review.dto.ReviewAddRequest;
import com.mogul.demo.review.dto.ReviewResponse;
import com.mogul.demo.review.dto.ReviewUpdateRequest;
import com.mogul.demo.review.mapper.ReviewMapper;
import com.mogul.demo.review.repository.ReviewRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ReviewServiceImpl implements ReviewService {

    private final ReviewRepository reviewRepository;

    @Override
    @Transactional(readOnly = true)
    public List<ReviewResponse> findReviewsByWebtoonId(Long webtoonId, int pageNumber, int pageSize) {
        List data =  reviewRepository.findByWebtoonIdAndIsDeletedFalseOrderByRegisteredDateDesc(webtoonId, PageRequest.of(pageNumber, pageSize))
                .stream().map(ReviewMapper.INSTANCE::fromReviewEntityToReivewResponse).collect(Collectors.toList());
        if(data.isEmpty()){
            throw new EntityNotFoundException("해당 웹툰에 달린 리뷰가 없습니다.");
        }
        return data;
    }

    @Override
    public boolean addReview(ReviewAddRequest reviewAddRequest) {
        reviewRepository.save(ReviewMapper.INSTANCE.fromReviewAddRequestToReviewEntity(reviewAddRequest));
        return true;
    }

    @Override
    @Transactional
    public boolean modifyReview(ReviewUpdateRequest reviewUpdateRequest) {
        if(!reviewRepository.existsByIdAndIsDeletedFalse(reviewUpdateRequest.getId())){
            throw new EntityNotFoundException("해당 아이디의 리뷰가 존재하지 않습니다.");
        }
        reviewRepository.updateReviewById(reviewUpdateRequest.getId(), reviewUpdateRequest.getTitle(), reviewUpdateRequest.getContent(), reviewUpdateRequest.getDrawingScore(), reviewUpdateRequest.getStoryScore(), reviewUpdateRequest.getDirectingScore());
        return true;
    }

    @Override
    @Transactional
    public boolean removeReview(Long id) {
        if(!reviewRepository.existsByIdAndIsDeletedFalse(id)){
            throw new EntityNotFoundException("해당 아이디의 리뷰가 존재하지 않습니다.");
        }
        reviewRepository.updateIsDeletedById(id);
        return true;
    }
}
