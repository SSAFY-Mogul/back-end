package com.mogul.demo.recommend.repository;

import com.mogul.demo.recommend.entity.EmbeddingEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface EmbeddingRepository extends JpaRepository<EmbeddingEntity, Long> {
    @Query("select e.embedding from EmbeddingEntity e where e.webtoonId=:webtoonId")
    String findByWebtoonId(@Param("webtoonId") Long webtoonId);
}
