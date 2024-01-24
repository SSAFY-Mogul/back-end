package com.mogul.demo.webtoon.repository;

import com.mogul.demo.webtoon.dto.WebtoonResponse;
import com.mogul.demo.webtoon.entity.WebtoonEntity;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface WebtoonRepository extends JpaRepository<WebtoonEntity, Long> {

    Optional<List<WebtoonEntity>> findAllByIsDeletedFalseOrderByGrade(Pageable pageable);

    Optional<List<WebtoonEntity>> getWebtoonByIsDeletedFalseOrderByTitle(Pageable pageable);
}
