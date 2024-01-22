package com.mogul.demo.review.repository;

import com.mogul.demo.review.entity.ReviewEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReviewRepository extends JpaRepository<ReviewEntity, Long> {

    @Query("select * from ReviewEntity r where r.webtoonId=:webtoonId order by r.registeredDate desc")
    List<ReviewEntity> findByWebtoonId(@Param("webtoonId") long webtoonId);
}
