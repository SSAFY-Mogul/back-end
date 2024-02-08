package com.mogul.demo.review.service;

import com.mogul.demo.review.dto.ReviewAddRequest;
import com.mogul.demo.review.dto.ReviewResponse;
import com.mogul.demo.review.dto.ReviewUpdateRequest;
import com.mogul.demo.review.entity.ReviewEntity;
import com.mogul.demo.review.mapper.ReviewMapper;
import com.mogul.demo.review.repository.ReviewNicknameRepository;
import com.mogul.demo.review.repository.ReviewRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ReviewServiceImpl implements ReviewService {

    private final ReviewRepository reviewRepository;

    private final ReviewNicknameRepository reviewNicknameRepository;

    @Override
    @Transactional(readOnly = true)
    public List<ReviewResponse> findReviewsByWebtoonId(Long webtoonId, int pageNumber, int pageSize) {
        List data =  reviewNicknameRepository.findByWebtoonIdAndIsDeletedFalseOrderByRegisteredDateDesc(webtoonId, PageRequest.of(pageNumber, pageSize))
                .stream().map(ReviewMapper.INSTANCE::fromReviewNicknameEntityToReivewResponse).collect(Collectors.toList());
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

    @Override
    @Transactional(readOnly = true)
    public Long findUser(Long id) {
        Optional<ReviewEntity> data = reviewRepository.findById(id);
        if(data.isEmpty()){
            throw new EntityNotFoundException("존재하지 않는 리뷰");
        }
        return data.get().getUserId();
    }

    @Override
    @Transactional(readOnly = true)
    public List<ReviewResponse> findReviewMy(Long userId, int pageNumber, int pageSize) {
        List<ReviewResponse> data = reviewNicknameRepository.findByUserIdOrderByRegisteredDateDesc(userId, PageRequest.of(pageNumber, pageSize)).stream().map(ReviewMapper.INSTANCE::fromReviewNicknameEntityToReivewResponse).collect(Collectors.toList());
        if(data.isEmpty()){
            throw new EntityNotFoundException("리뷰가 존재하지 않습니다.");
        }
        return data;
    }
}
