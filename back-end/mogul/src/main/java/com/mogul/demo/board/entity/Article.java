package com.mogul.demo.board.entity;

import java.time.LocalDateTime;
import java.util.List;

import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.mogul.demo.board.dto.ArticleUpdateRequest;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@SuppressWarnings("checkstyle:RegexpMultiline")
@Data
@Entity
@Table(name = "article")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Article {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "article_id",nullable = false)
	private long id;

	@Column(name = "article_title",nullable = false)
	private String title;

	@Column(name = "article_content",nullable = false)
	private String content;

	@CreationTimestamp
	@Column(name = "article_registered_date",nullable = false)
	private LocalDateTime registeredDate;

	@UpdateTimestamp
	@Column(name = "article_edited_date")
	private LocalDateTime editedDate;

	@Column(name = "article_deleted_date")
	private LocalDateTime deletedDate;

	@Column(name = "article_hit",nullable = false)
	@ColumnDefault("0")
	private int hit;

	@Column(name = "article_is_deleted",nullable = false)
	@ColumnDefault("0")
	private int isDeleted;

	@Column(name ="user_id",nullable = false)
	private int userId;

	@OneToMany(mappedBy = "article", cascade = CascadeType.ALL)
	private List<Comment> comments;
	//여러개의 댓글을 가지고 있음
	//mappedBy를 통해서 댓글 엔티티가 참조하는 필드가 게시글임을 나타냄

	public void deleteArticle(){
		this.setIsDeleted(1);
		this.setDeletedDate(LocalDateTime.now());
	}

	public void updateArticle(String title,String content){
		this.setTitle(title);
		this.setContent(content);
	}
	public void updateHit(){
		this.setHit(this.getHit()+1);
	}
}
