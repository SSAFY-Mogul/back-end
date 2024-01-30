package com.mogul.demo.library.repository;

import com.mogul.demo.library.dto.SubscriptionResponse;
import com.mogul.demo.library.entity.LibrarySubscriptionThumbnailEntity;
import com.mogul.demo.library.entity.LibrarySubscriptionThumbnailPK;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LibrarySubscriptionThumbnailRepository extends JpaRepository<LibrarySubscriptionThumbnailEntity, LibrarySubscriptionThumbnailPK> {

    List<LibrarySubscriptionThumbnailEntity> findByUserId(long userId, Pageable pageable);
}
