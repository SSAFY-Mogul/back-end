package com.mogul.demo.webtoon.repository;

import com.mogul.demo.webtoon.dto.WebtoonTagResponse;
import com.mogul.demo.webtoon.entity.WebtoonWebtoonTagTagEntity;
import com.mogul.demo.webtoon.entity.WebtoonWebtoonTagTagPK;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WebtoonWebtoonTagTagRepository extends JpaRepository<WebtoonWebtoonTagTagEntity, WebtoonWebtoonTagTagPK> {

    List<WebtoonWebtoonTagTagEntity> findByWebtoonId(long webtoonId);
}
