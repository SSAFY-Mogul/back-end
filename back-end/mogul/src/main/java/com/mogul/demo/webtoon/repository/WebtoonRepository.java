package com.mogul.demo.webtoon.repository;

import com.mogul.demo.webtoon.entity.WebtoonEntity;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.sql.SQLException;
import java.util.List;

@Repository
public interface WebtoonRepository extends JpaRepository<WebtoonEntity, Long> {

    @Query("select w from WebtoonEntity w order by w.grade")
    List<WebtoonEntity> findMain(Pageable pageable) throws SQLException;

    @Query("select w from WebtoonEntity w order by w.title")
    List<WebtoonEntity> findAllByTitle(Pageable pageable) throws SQLException;

    @Query("select w from WebtoonEntity w where w.genre=:genre order by w.title")
    List<WebtoonEntity> findAllByGenre(@Param("genre") String genre, Pageable pageable) throws SQLException;

    @Query("select w from WebtoonEntity w where w.id=:id")
    WebtoonEntity find(@Param("id") long id) throws SQLException;
}
