package com.mogul.demo.board.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;

@Data
@Entity
@Table(name = "comment")
public class Comment {

	@Id @GeneratedValue(strategy = GenerationType.IDENTITY) @Column(name="comment_id")
	private int id;

	@Column(name="comment_content")
	private String content;

	@CreationTimestamp
	@Column(name="comment_registered_date",nullable = false)
	private LocalDateTime registeredDate;

	@Column(name="comment_deleted_date",nullable = false)
	private LocalDateTime deletedDate;

	@Column(name="comment_is_deleted")
	private int isDeleted;

	@Column(name="comment_group")
	private int group;

	// @OneToOne
	// @JoinColumn(name="user_id")
	@Column(name="user_id")
	private int userId;

	// @ManyToOne
	// @JoinColumn(name="article_id")
	@Column(name="article_id")
	private int article;

	public void deleteComment(){
		this.setDeletedDate(LocalDateTime.now());
		this.setIsDeleted(1);
		this.setContent("삭제된 댓글입니다");
	}

}

