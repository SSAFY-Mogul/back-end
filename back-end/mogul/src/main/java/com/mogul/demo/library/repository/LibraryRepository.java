package com.mogul.demo.library.repository;

import com.mogul.demo.library.entity.LibraryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface LibraryRepository extends JpaRepository<LibraryEntity, Long> {

    @Query("select case when count(l)=1 then true else false end from LibraryEntity l where l.id=:id and l.isDeleted=false")
    boolean existsByIdAndIsDeletedFalse(@Param("id") Long id);

    @Modifying(clearAutomatically = true)
    @Query("update LibraryEntity l set l.isDeleted=true, l.deletedDate=current_timestamp where l.id=:id")
    void updateIsDeleted(@Param("id") Long id);

    @Modifying(clearAutomatically = true)
    @Query("update LibraryEntity l set l.subscriberNumber=:subscriberNumber where l.id=:id")
    void updateSubscriberNumberById(@Param("id") Long id, @Param("subscriberNumber") Long subscriberNumber);

    @Transactional
    @Modifying(clearAutomatically = true)
    @Query("update LibraryEntity l set l.name=:name where l.id=:id")
    void updateNameById(@Param("id") Long id, @Param("name") String name);

    @Query("select min(l.id) from LibraryEntity l where l.isDeleted=false")
    Long getMinId();

    @Query("select max(l.id) from LibraryEntity  l where l.isDeleted=false")
    Long getMaxId();

    @Query("select l.isDeleted from LibraryEntity l where l.id=:id")
    boolean findIsDeletedById(@Param("id") Long id);

    @Query("select count(l) from LibraryEntity l where l.userId=:userId and l.isDeleted=false")
    int countByUserIdAndIsDeletedFalse(@Param("userId") Long userId);
}
