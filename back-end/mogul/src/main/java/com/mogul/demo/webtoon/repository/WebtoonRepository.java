package com.mogul.demo.webtoon.repository;

import com.mogul.demo.webtoon.entity.WebtoonEntity;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
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

    WebtoonEntity findOneByIdAndIsDeletedFalse(long id);

    @Query("select case when count(w)=1 then true else false end from WebtoonEntity w where w.id=:id and w.isDeleted=false")
    boolean existsByIdAndIsDeletedFalse(@Param("id") long id);

    @Query("update WebtoonEntity w set w.grade=:grade, w.drawingGrade=:drawingGrade, w.storyGrade=:storyGrade, w.directingGrade=:directingGrade where w.id=:id and w.isDeleted=false")
    void updateGrade(@Param("id") long id, @Param("grade") float grade, @Param("drawingGrade") float drawingGrade, @Param("storyGrade") float storyGrade, @Param("directingGrade") float directingGrade);
}
