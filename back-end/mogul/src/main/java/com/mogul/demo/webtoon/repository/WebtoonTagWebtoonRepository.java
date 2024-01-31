package com.mogul.demo.webtoon.repository;

import com.mogul.demo.webtoon.entity.WebtoonTagWebtoonPK;
import com.mogul.demo.webtoon.entity.WebtoonTagWebtoonEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WebtoonTagWebtoonRepository extends JpaRepository<WebtoonTagWebtoonEntity, WebtoonTagWebtoonPK> {

    List<WebtoonTagWebtoonEntity> findByTagIdAndIsDeletedFalse(Long tagId);
}
