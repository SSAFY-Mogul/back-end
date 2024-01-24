package com.mogul.demo.review.repository;

import com.mogul.demo.review.dto.ReviewResponse;
import com.mogul.demo.review.entity.ReviewEntity;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ReviewRepository extends JpaRepository<ReviewEntity, Long> {

    Optional<List<ReviewEntity>> findAllByWebtoonIdAndIsDeletedFalseOrderByRegisteredDateDesc(long webtoonId, Pageable pageable);
}
