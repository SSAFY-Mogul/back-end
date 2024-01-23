package com.mogul.demo.webtoon.repository;

import com.mogul.demo.webtoon.entity.WebtoonCount;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WebtoonCntRepository extends JpaRepository<WebtoonCount, Long> {

    @Query("select wc from WebtoonCount wc where wc.isDeleted=false order by wc.cnt")
    List<WebtoonCount> findMain(Pageable pageable);
}
