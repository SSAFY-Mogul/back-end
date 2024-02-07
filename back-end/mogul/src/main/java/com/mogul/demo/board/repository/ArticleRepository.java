package com.mogul.demo.board.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.mogul.demo.board.entity.Article;
import com.mogul.demo.user.entity.User;

@Repository
public interface ArticleRepository extends JpaRepository<Article,Long> {

	Optional<Article> findArticleById(Long id);
	List<Article> findAllByIsDeletedFalse(Pageable pageable);
	List<Article> findAllByIsDeletedFalseAndUser(Pageable pageable, User user);
	int countArticleByIsDeletedFalse();
}
