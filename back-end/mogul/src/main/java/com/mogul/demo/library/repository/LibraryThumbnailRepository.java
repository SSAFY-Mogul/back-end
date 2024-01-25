package com.mogul.demo.library.repository;

import com.mogul.demo.library.entity.LibraryThumbnailEntity;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface LibraryThumbnailRepository extends JpaRepository<LibraryThumbnailEntity, Long> {

    @Query("select lt from LibraryThumbnailEntity lt where lt.isDeleted=false order by lt.subscriberNumber")
    List<LibraryThumbnailEntity> findByIsDeletedFalseOrderBySubscriberNumber(Pageable pageable);

    List<LibraryThumbnailEntity> findByUserIdAndIsDeletedFalseOrderByRegisteredDateDesc(long userId);

    LibraryThumbnailEntity findOneById(long id);
}
