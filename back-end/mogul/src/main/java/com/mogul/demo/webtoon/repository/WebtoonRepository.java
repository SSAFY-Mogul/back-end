package com.mogul.demo.webtoon.repository;

import com.mogul.demo.webtoon.entity.WebtoonEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WebtoonRepository extends JpaRepository<WebtoonEntity, Long> {

}
