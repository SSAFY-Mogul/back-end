package com.mogul.demo.board.entity;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table(name="article_article_tag")
@NoArgsConstructor
@AllArgsConstructor
public class ArticleArticleTag {

	@EmbeddedId
	private ArticleArticleTagPK id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="article_id",insertable = false, updatable = false)
	private Article article;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="article_tag_id",insertable = false, updatable = false)
	private ArticleTag articleTag;


}
