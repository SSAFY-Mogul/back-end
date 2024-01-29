package com.mogul.demo.board.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mogul.demo.board.dto.CommentCreateRequest;
import com.mogul.demo.board.dto.CommentGroupResponse;
import com.mogul.demo.board.dto.CommentReadResponse;
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
		List<Comment> parentsComment = commentRepository.findParentComments(articleId);

		for(Comment parent : parentsComment){
			List<Comment> childredComment = commentRepository.findChildCommentsByParentId(parent.getId());
			CommentGroupResponse commentGroupResponse = CommentMapper.INSTANCE.commentToCommentGroupResponse(parent);
			commentGroupResponse.setChildren(CommentMapper.INSTANCE.mapToChildren(childredComment));
			commentGroupResponseList.add(commentGroupResponse);
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
	public boolean removeComment(int id) {
		Optional<Comment> comment = Optional.ofNullable(
			commentRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("해당 댓글을 찾을 수 없습니다")));
		comment.get().deleteComment();
		commentRepository.save(comment.get());
		return true;
	}
}
