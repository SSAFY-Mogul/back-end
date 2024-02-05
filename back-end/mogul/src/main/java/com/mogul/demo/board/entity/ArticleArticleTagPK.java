package com.mogul.demo.board.entity;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
public class ArticleArticleTagPK implements Serializable {

	@Column(name = "article_id")
	private Long articleId;
	@Column(name= "article_tag_id")
	private Long articleTagId;
}
