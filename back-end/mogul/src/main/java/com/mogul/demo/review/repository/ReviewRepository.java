package com.mogul.demo.review.repository;

import com.mogul.demo.review.entity.ReviewEntity;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ReviewRepository extends JpaRepository<ReviewEntity, Long> {

    List<ReviewEntity> findByWebtoonIdAndIsDeletedFalseOrderByRegisteredDateDesc(long webtoonId, Pageable pageable);

    @Query("select avg(r.drawingScore) from ReviewEntity r where r.webtoonId=:webtoonId and r.isDeleted=false")
    float avgDrawingScoreByWebtoonId(@Param("webtoonId") long webtoonId);

    @Query("select avg(r.storyScore) from ReviewEntity r where r.webtoonId=:webtoonId and r.isDeleted=false")
    float avgStoryScoreByWebtoonId(@Param("webtoonId") long webtoonId);

    @Query("select avg(r.directingScore) from ReviewEntity r where r.webtoonId=:webtoonId and r.isDeleted=false")
    float avgDirectingScoreByWebtoonId(@Param("webtoonId") long webtoonId);

    @Query("select case when count(r)=1 then true else false end from ReviewEntity r where r.id=:id and r.isDeleted=false")
    boolean existsByIdAndIsDeletedFalse(@Param("id") long id);

    @Query("update ReviewEntity r set r.title=:title, r.content=:content, r.drawingScore=:drawingScore, r.storyScore=:storyScore, r.directingScore=:directingScore where r.id=:id")
    @Modifying(clearAutomatically = true)
    void updateReviewById(@Param("id") long id, @Param("title") String title, @Param("content") String content, @Param("drawingScore") int drawingScore, @Param("storyScore") int storyScore, @Param("directingScore") int directingScore);

    @Query("update ReviewEntity r set r.isDeleted=true, r.deletedDate=current_timestamp where r.id=:id")
    @Modifying(clearAutomatically = true)
    void updateIsDeletedById(@Param("id") long id);
}
