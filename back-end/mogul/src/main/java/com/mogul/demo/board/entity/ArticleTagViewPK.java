package com.mogul.demo.board.entity;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ArticleTagViewPK implements Serializable {
	private Long id;
	private Long tagId;
}
