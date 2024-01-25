package com.mogul.demo.board.mapper;

import java.util.List;
import java.util.stream.Collectors;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

import com.mogul.demo.board.dto.CommentCreateRequest;
import com.mogul.demo.board.dto.CommentGroupResponse;
import com.mogul.demo.board.dto.CommentReadResponse;
import com.mogul.demo.board.entity.Comment;

@Mapper
public interface CommentMapper {
	CommentMapper INSTANCE = Mappers.getMapper(CommentMapper.class);

	// Comment <-> CommentReadResponse
	Comment commentReadResponseToComment(CommentReadResponse commentReadResponse);

	CommentReadResponse commentToCommentReadResponse(Comment comment);

	// Comment <-> CommentCreateRequest
	Comment commentCreateRequestToComment(CommentCreateRequest commentCreateRequest);

	CommentCreateRequest commentCreateRequestToComment(Comment comment);

	CommentGroupResponse commentToCommentGroupResponse(Comment comment);
	Comment commentGroupResponseToComment(CommentGroupResponse commentGroupResponse);

	default List<CommentReadResponse> mapToChildren(List<Comment> comments) {
		return comments.stream()
			.map(this::commentToCommentReadResponse)
			.collect(Collectors.toList());
	}
}
