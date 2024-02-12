package com.mogul.demo.board.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mogul.demo.board.entity.ArticleTagView;

public interface ArticleTagViewRepository extends JpaRepository<ArticleTagView,Long> {

	List<ArticleTagView> findArticleTagViewById(Long id);
	List<ArticleTagView> findArticleTagViewByTag(String tag);
}
