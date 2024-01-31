package com.mogul.demo.webtoon.repository;

import com.mogul.demo.webtoon.entity.WebtoonLibraryEntity;
import com.mogul.demo.webtoon.entity.WebtoonLibraryPK;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface WebtoonLibraryRepository extends JpaRepository<WebtoonLibraryEntity, WebtoonLibraryPK> {
    List<WebtoonLibraryEntity> findAllByLibraryIdAndIsDeletedFalseOrderByTitleAsc(Long libraryId);
}
