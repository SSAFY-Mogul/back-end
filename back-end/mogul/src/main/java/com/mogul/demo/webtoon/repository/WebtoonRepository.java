package com.mogul.demo.webtoon.repository;

import com.mogul.demo.webtoon.entity.WebtoonEntity;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface WebtoonRepository extends JpaRepository<WebtoonEntity, Long> {

    List<WebtoonEntity> findByIsDeletedFalseOrderByGradeDesc(Pageable pageable);

    List<WebtoonEntity> findByIsDeletedFalseOrderByTitle(Pageable pageable);

    List<WebtoonEntity> findByGenreAndIsDeletedFalseOrderByTitleAsc(String genre, Pageable pageable);

    Optional<WebtoonEntity> findOneByIdAndIsDeletedFalse(long id);

    @Query("select case when count(w)=1 then true else false end from WebtoonEntity w where w.id=:id and w.isDeleted=false")
    boolean existsByIdAndIsDeletedFalse(@Param("id") Long id);

    @Modifying
    @Query("update WebtoonEntity w set w.grade=:grade, w.drawingGrade=:drawingGrade, w.storyGrade=:storyGrade, w.directingGrade=:directingGrade where w.id=:id and w.isDeleted=false")
    void updateGrade(@Param("id") Long id, @Param("grade") Float grade, @Param("drawingGrade") Float drawingGrade, @Param("storyGrade") Float storyGrade, @Param("directingGrade") Float directingGrade);

    @Query("select min(w.id) from WebtoonEntity w where w.isDeleted=false")
    Long findMinId();

    @Query("select max(w.id) from WebtoonEntity w where w.isDeleted=false")
    Long findMaxId();

    @Query("select w.isDeleted from WebtoonEntity w where w.id=:id")
    boolean findIsDeletedById(@Param("id") Long id);

    boolean findIsEmbeddedById(Long webtoonId);
}
