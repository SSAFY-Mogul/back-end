package com.mogul.demo.library.repository;

import com.mogul.demo.library.entity.LibraryWebtoonEntity;
import com.mogul.demo.library.entity.LibraryWebtoonPK;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LibraryWebtoonRepository extends JpaRepository<LibraryWebtoonEntity, LibraryWebtoonPK> {
}
