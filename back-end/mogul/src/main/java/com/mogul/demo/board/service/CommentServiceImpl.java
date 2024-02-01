package com.mogul.demo.board.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mogul.demo.board.dto.CommentCreateRequest;
import com.mogul.demo.board.dto.CommentGroupResponse;
import com.mogul.demo.board.dto.CommentReadResponse;
import com.mogul.demo.board.dto.CommentResponse;
import com.mogul.demo.board.entity.Comment;
import com.mogul.demo.board.mapper.CommentMapper;
import com.mogul.demo.board.repository.CommentRepository;

import jakarta.persistence.EntityNotFoundException;

@Service
public class CommentServiceImpl implements CommentService{

	private final CommentRepository commentRepository;

	public CommentServiceImpl(CommentRepository commentRepository) {
		this.commentRepository = commentRepository;
	}

	@Override
	@Transactional(readOnly = true)
	public List<CommentGroupResponse> findCommentList(Long articleId) {
		List<CommentGroupResponse> commentGroupResponseList = new ArrayList<>();
		List<CommentGroupResponse> parentsComment = commentRepository
			.findParentComments(articleId)
			.stream()
			.map(CommentMapper.INSTANCE::commentToCommentGroupResponse)
			.collect(Collectors.toList());;

		for(CommentGroupResponse parent : parentsComment){
			List<CommentReadResponse> childrenComment = commentRepository
				.findChildCommentsByParentId((long)parent.getId())
				.stream()
				.map(CommentMapper.INSTANCE::commentToCommentReadResponse)
				.collect(Collectors.toList());

			parent.setChildren(childrenComment);
			commentGroupResponseList.add(parent);
		}

		return commentGroupResponseList;
	}

	@Override
	@Transactional
	public CommentReadResponse addComment(CommentCreateRequest commentCreateRequest) {
		Comment comment = CommentMapper.INSTANCE.commentCreateRequestToComment(commentCreateRequest);
		CommentReadResponse createdComment = CommentMapper.INSTANCE.commentToCommentReadResponse(commentRepository.save(comment));
		return createdComment;
	}

	@Override
	@Transactional
	public boolean removeComment(Long id) {
		Optional<Comment> comment = Optional.ofNullable(
			commentRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("해당 댓글을 찾을 수 없습니다")));
		comment.get().deleteComment();
		commentRepository.save(comment.get());
		return true;
	}
}
