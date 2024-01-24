package com.mogul.demo.webtoon.repository;

import com.mogul.demo.webtoon.dto.WebtoonDetailResponse;
import com.mogul.demo.webtoon.entity.WebtoonEntity;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

@Repository
public interface WebtoonRepository extends JpaRepository<WebtoonEntity, Long> {

    Optional<List<WebtoonEntity>> getWebtoonEntityByIsDeletedFalseOrderByGrade(Pageable pageable);

    Optional<List<WebtoonEntity>> getWebtoonEntityByIsDeletedFalseOrderByTitle(Pageable pageable);

    Optional<List<WebtoonEntity>> getWebtoonEntityByGenreAndIsDeletedFalseOrderByTitle(String genre, Pageable pageable);

    Optional<WebtoonEntity> findOneById(long id);
}
