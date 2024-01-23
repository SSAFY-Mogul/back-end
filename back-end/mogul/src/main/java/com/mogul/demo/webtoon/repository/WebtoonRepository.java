package com.mogul.demo.webtoon.repository;

import com.mogul.demo.webtoon.dto.WebtoonDetailDto;
import com.mogul.demo.webtoon.entity.Webtoon;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.sql.SQLException;
import java.util.List;

@Repository
public interface WebtoonRepository extends JpaRepository<Webtoon, Long> {

    @Query("select w from Webtoon w where w.isDeleted=false order by w.grade")
    List<Webtoon> findMain(Pageable pageable) throws SQLException;

    @Query("select w from Webtoon w where w.isDeleted=false order by w.title")
    List<Webtoon> findAllByTitle(Pageable pageable) throws SQLException;

    @Query("select w from Webtoon w where w.isDeleted=false and w.genre=:genre order by w.title")
    List<Webtoon> findAllByGenre(@Param("genre") String genre, Pageable pageable) throws SQLException;

    @Query("select w from Webtoon w where w.isDeleted=false and w.id=:id")
    WebtoonDetailDto find(@Param("id") long id) throws SQLException;
}
