package com.mogul.demo.board.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mogul.demo.board.entity.ArticleArticleTag;
import com.mogul.demo.board.entity.ArticleArticleTagPK;

public interface ArticleArticleTagRepository extends JpaRepository<ArticleArticleTag, ArticleArticleTagPK> {
}
