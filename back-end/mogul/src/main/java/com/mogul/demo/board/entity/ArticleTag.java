package com.mogul.demo.board.entity;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "article_tag")
public class ArticleTag {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "article_tag_id",nullable = false)
	private Long id;

	@Column(name = "article_tag",nullable = false,unique = true)
	private String tag;

	@OneToMany(mappedBy = "articleTag")
	private List<ArticleArticleTag> articleArticleTags = new ArrayList<>();
}

