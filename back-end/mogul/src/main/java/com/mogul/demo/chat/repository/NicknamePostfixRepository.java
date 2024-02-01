package com.mogul.demo.chat.repository;

import com.mogul.demo.chat.entity.NicknamePostfixEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NicknamePostfixRepository extends JpaRepository<NicknamePostfixEntity, Long> {
    NicknamePostfixEntity findOneById(Long id);
}
