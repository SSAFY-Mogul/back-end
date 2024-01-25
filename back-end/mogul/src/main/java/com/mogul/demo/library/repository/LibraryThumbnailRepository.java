package com.mogul.demo.library.repository;

import com.mogul.demo.library.dto.LibraryResponse;
import com.mogul.demo.library.entity.LibraryThumbnailEntity;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface LibraryThumbnailRepository extends JpaRepository<LibraryThumbnailEntity, Long> {

    Optional<List<LibraryThumbnailEntity>> findAllByIsDeletedFalseOrderBySubscriberNumber(Pageable pageable);

    Optional<List<LibraryThumbnailEntity>> findAllByUserIdAndDeletedFalseOrderByRegisteredDateDesc(long userId);

    Optional<LibraryThumbnailEntity> findOneById(long id);
}
