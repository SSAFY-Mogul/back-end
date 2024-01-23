package com.mogul.demo.webtoon.repository;

import com.mogul.demo.webtoon.entity.WebtoonCountEntity;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface WebtoonCountRepository extends JpaRepository<WebtoonCountEntity, Long> {
    Optional<List<WebtoonCountEntity>> getWebtoonCountEntityByIsDeletedFalseOrderByCount(Pageable pageable);
}
