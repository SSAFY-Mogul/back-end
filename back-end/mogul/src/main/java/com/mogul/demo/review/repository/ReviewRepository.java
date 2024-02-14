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

    @Query("select case when avg(r.drawingScore) is null then 0 else avg(r.drawingScore) end from ReviewEntity r where r.webtoonId=:webtoonId and r.isDeleted=false")
    float avgDrawingScoreByWebtoonId(@Param("webtoonId") Long webtoonId);

    @Query("select case when avg(r.storyScore) is null then 0 else avg(r.storyScore) end from ReviewEntity r where r.webtoonId=:webtoonId and r.isDeleted=false")
    float avgStoryScoreByWebtoonId(@Param("webtoonId") Long webtoonId);

    @Query("select case when avg(r.directingScore) is null then 0 else avg(r.directingScore) end from ReviewEntity r where r.webtoonId=:webtoonId and r.isDeleted=false")
    float avgDirectingScoreByWebtoonId(@Param("webtoonId") Long webtoonId);

    @Query("select case when count(r)=1 then true else false end from ReviewEntity r where r.id=:id and r.isDeleted=false")
    boolean existsByIdAndIsDeletedFalse(@Param("id") Long id);

    @Modifying
    @Query("update ReviewEntity r set r.title=:title, r.content=:content, r.drawingScore=:drawingScore, r.storyScore=:storyScore, r.directingScore=:directingScore where r.id=:id")
    void updateReviewById(@Param("id") Long id, @Param("title") String title, @Param("content") String content, @Param("drawingScore") Integer drawingScore, @Param("storyScore") Integer storyScore, @Param("directingScore") Integer directingScore);

    @Query("update ReviewEntity r set r.isDeleted=true, r.deletedDate=current_timestamp where r.id=:id")
    @Modifying
    void updateIsDeletedById(@Param("id") Long id);

    @Query("select distinct r.webtoonId from ReviewEntity r where r.userId=:userId and r.isDeleted=false order by (r.drawingScore + r.storyScore + r.directingScore) desc limit 5")
    List<Long> findByUserIdOrderByAvgScore(@Param("userId") Long userId);
}
