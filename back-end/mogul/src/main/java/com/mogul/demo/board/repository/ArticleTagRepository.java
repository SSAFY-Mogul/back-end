package com.mogul.demo.board.repository;

import java.util.Optional;

import javax.swing.text.html.Option;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.mogul.demo.board.entity.ArticleTag;

@Repository
public interface ArticleTagRepository extends JpaRepository<ArticleTag,Long> {
	@Query("SELECT COUNT(t) > 0 FROM ArticleTag t WHERE t.tag = :tag")
	Boolean existsByTag(String tag);

	Optional<ArticleTag> findArticleTagByTag(String tag);
}
