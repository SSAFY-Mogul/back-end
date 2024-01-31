package com.mogul.demo.webtoon.repository;

import com.mogul.demo.webtoon.dto.WebtoonLikeResponse;
import com.mogul.demo.webtoon.entity.WebtoonLikeEntity;
import com.mogul.demo.webtoon.entity.WebtoonLikePK;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface WebtoonLikeRepository extends JpaRepository<WebtoonLikeEntity, WebtoonLikePK> {

    Optional<WebtoonLikeEntity> findByWebtoonIdAndUserId(long webtoonId, long userId);
}
