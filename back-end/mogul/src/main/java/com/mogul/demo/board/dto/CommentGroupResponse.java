package com.mogul.demo.board.dto;

import java.util.List;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
public class CommentGroupResponse extends CommentReadResponse {
	List<CommentReadResponse> children; // 자식개체
}
