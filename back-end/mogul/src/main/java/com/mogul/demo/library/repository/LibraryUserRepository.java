package com.mogul.demo.library.repository;

import com.mogul.demo.library.entity.LibraryUserEntity;
import com.mogul.demo.library.entity.LibraryUserPK;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface LibraryUserRepository extends JpaRepository<LibraryUserEntity, LibraryUserPK> {

    @Query("select case when count(lu)=1 then true else false end from LibraryUserEntity lu where lu.libraryId=:libraryId and lu.userId=:userId")
    boolean existsByLibraryIdAndUserId(@Param("libraryId") Long libraryId,@Param("userId") Long userId);

    @Query("select count(*) from LibraryUserEntity lu where lu.libraryId=:libraryId")
    long countByLibraryId(@Param("libraryId") Long libraryId);
}
