package com.mogul.demo.chat.repository;

import com.mogul.demo.chat.entity.NicknamePrefixEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NicknamePrefixRepository extends JpaRepository<NicknamePrefixEntity, Long> {

    NicknamePrefixEntity findOneById(Long id);
}
