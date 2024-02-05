package com.mogul.demo.board.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Table(name = "article_tag_view")
@Entity
@IdClass(ArticleTagViewPK.class)
public class ArticleTagView {

	@Id
	@Column(name = "article_id")
	private Long id;
	@Id
	@Column(name="article_tag_id")
	private Long tagId;
	@Column(name = "article_tag")
	private String tag;
}
