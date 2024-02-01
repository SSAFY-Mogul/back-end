package com.mogul.demo.library.repository;

import com.mogul.demo.library.entity.LibraryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface LibraryRepository extends JpaRepository<LibraryEntity, Long> {

    @Query("select case when count(l)=1 then true else false end from LibraryEntity l where l.id=:id and l.isDeleted=false")
    boolean existsByIdAndIsDeletedFalse(@Param("id") Long id);

    @Transactional
    @Modifying(clearAutomatically = true)
    @Query("update LibraryEntity l set l.isDeleted=true, l.deletedDate=current_timestamp where l.id=:id")
    void updateIsDeleted(@Param("id") Long id);

    @Transactional
    @Modifying(clearAutomatically = true)
    @Query("update LibraryEntity l set l.subscriberNumber=l.subscriberNumber+1 where l.id=:id")
    void updateSubscriberNumberById(@Param("id") Long id);

    @Transactional
    @Modifying(clearAutomatically = true)
    @Query("update LibraryEntity l set l.name=:name where l.id=:id")
    void updateNameById(@Param("id") Long id, @Param("name") String name);
}
