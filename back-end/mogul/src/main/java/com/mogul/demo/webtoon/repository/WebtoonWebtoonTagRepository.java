package com.mogul.demo.webtoon.repository;

import com.mogul.demo.webtoon.entity.WebtoonWebtoonTagEntity;
import com.mogul.demo.webtoon.entity.WebtoonWebtoonTagPK;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WebtoonWebtoonTagRepository extends JpaRepository<WebtoonWebtoonTagEntity, WebtoonWebtoonTagPK> {
}
