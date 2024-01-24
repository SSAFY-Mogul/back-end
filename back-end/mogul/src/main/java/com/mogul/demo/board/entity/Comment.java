package com.mogul.demo.board.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "comment")
public class Comment {

	@Id @GeneratedValue(strategy = GenerationType.IDENTITY) @Column(name="comment_id")
	private Long id;

	@Column(name="comment_content")
	private String content;

	@Column(name="comment_registered_date")
	private LocalDateTime registeredDate;

	@Column(name="comment_deleted_date")
	private LocalDateTime deletedDate;

	@Column(name="comment_is_deleted")
	private int isDeleted;

	@Column(name="comment_group")
	private int group;

	@ManyToOne
	@JoinColumn(name="article_id")
	private Article article;

}

