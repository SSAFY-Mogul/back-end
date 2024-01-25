package com.mogul.demo.webtoon.repository;

import com.mogul.demo.webtoon.entity.WebtoonEntity;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface WebtoonRepository extends JpaRepository<WebtoonEntity, Long> {

    List<WebtoonEntity> findByIsDeletedFalseOrderByGradeDesc(Pageable pageable);

    List<WebtoonEntity> findByIsDeletedFalseOrderByTitle(Pageable pageable);

    List<WebtoonEntity> findByGenreAndIsDeletedFalseOrderByTitleAsc(String genre, Pageable pageable);

    WebtoonEntity findOneByIdAndIsDeletedFalse(long id);
}
