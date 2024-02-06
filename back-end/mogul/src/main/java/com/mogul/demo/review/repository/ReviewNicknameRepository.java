package com.mogul.demo.review.repository;

import com.mogul.demo.review.entity.ReviewEntity;
import com.mogul.demo.review.entity.ReviewNicknameEntity;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReviewNicknameRepository extends JpaRepository<ReviewNicknameEntity, Long> {
    List<ReviewNicknameEntity> findByWebtoonIdAndIsDeletedFalseOrderByRegisteredDateDesc(Long webtoonId, Pageable pageable);
}
