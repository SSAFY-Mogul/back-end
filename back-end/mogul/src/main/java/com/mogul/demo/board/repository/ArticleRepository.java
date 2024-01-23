package com.mogul.demo.board.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.mogul.demo.board.entity.Article;

@Repository
public interface ArticleRepository extends JpaRepository<Article,Long> {

	Optional<Article> getArticleById(int id);
	Optional<Article> getArticlesByIsDeletedFalseAndId(int id);

}
