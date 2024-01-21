package com.mogul.demo.webtoon.repository;

import com.mogul.demo.webtoon.entity.WebtoonCntEntity;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WebtoonCntRepository extends JpaRepository<WebtoonCntEntity, Long> {

    @Query("select wc from WebtoonCntEntity wc order by wc.cnt")
    List<WebtoonCntEntity> find(Pageable pageable);
}
