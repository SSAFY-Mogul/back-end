package com.mogul.demo.library.repository;

import com.mogul.demo.library.entity.LibraryWebtoonEntity;
import com.mogul.demo.library.entity.LibraryWebtoonPK;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface LibraryWebtoonRepository extends JpaRepository<LibraryWebtoonEntity, LibraryWebtoonPK> {
    @Query("select count(lw) from LibraryWebtoonEntity lw where lw.libraryId=:libraryId")
    int countById(@Param("libraryId") Long libraryId);
}
