package com.mogul.demo.library.repository;

import com.mogul.demo.library.dto.LibraryResponse;
import com.mogul.demo.library.entity.LibraryWebtoonThumbnailEntity;
import com.mogul.demo.library.pk.LibraryWebtoonThumbnailPK;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@Repository
public interface LibraryWebtoonThumbnailRepository extends JpaRepository<LibraryWebtoonThumbnailEntity, LibraryWebtoonThumbnailPK> {

    Optional<List<LibraryWebtoonThumbnailEntity>> findAllByWebtoonIdAndIsDeletedFalseOrderBySubscriberNumberDesc(long webtoonId, Pageable pageable);

}
