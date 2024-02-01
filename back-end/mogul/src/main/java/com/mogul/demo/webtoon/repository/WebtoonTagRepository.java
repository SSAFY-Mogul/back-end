package com.mogul.demo.webtoon.repository;

import com.mogul.demo.webtoon.entity.WebtoonTagEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface WebtoonTagRepository extends JpaRepository<WebtoonTagEntity, Long> {

    @Query("select case when count(t)!=0 then true else false end from WebtoonTagEntity t where t.tag=:tag")
    boolean existsByTag(@Param("tag") String tag);
}
