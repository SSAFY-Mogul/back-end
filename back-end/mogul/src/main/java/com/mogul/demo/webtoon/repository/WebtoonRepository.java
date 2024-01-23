package com.mogul.demo.webtoon.repository;

import com.mogul.demo.webtoon.dto.WebtoonDetailDto;
import com.mogul.demo.webtoon.dto.WebtoonDto;
import com.mogul.demo.webtoon.entity.WebtoonEntity;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.swing.text.html.Option;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@Repository
public interface WebtoonRepository extends JpaRepository<WebtoonEntity, Long> {

    @Query("select w from WebtoonEntity w where w.isDeleted=false order by w.grade")
    Optional<List<WebtoonEntity>> findMain(Pageable pageable);

    Optional<List<WebtoonEntity>> findAllByIsDeletedFalse(Pageable pageable);

    @Query("select w from WebtoonEntity w where w.isDeleted=false and w.genre=:genre order by w.title")
    Optional<List<WebtoonEntity>> findAllByGenre(@Param("genre") String genre, Pageable pageable);

    @Query("select w from WebtoonEntity w where w.isDeleted=false and w.id=:id")
    Optional<WebtoonEntity> find(@Param("id") long id);

    Optional<List<WebtoonEntity>> findAllByIsDeletedFalseOrderByGrade(Pageable pageable);
}
