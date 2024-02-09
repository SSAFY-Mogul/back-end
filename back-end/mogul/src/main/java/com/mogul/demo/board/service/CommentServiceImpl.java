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
import com.mogul.demo.board.entity.Article;
import com.mogul.demo.board.entity.Comment;
import com.mogul.demo.board.mapper.CommentMapper;
import com.mogul.demo.board.repository.CommentRepository;
import com.mogul.demo.user.entity.User;
import com.mogul.demo.user.service.UserService;

import jakarta.persistence.EntityNotFoundException;

@Service
public class CommentServiceImpl implements CommentService{

	private final CommentRepository commentRepository;
	private final UserService userService;
	private final ArticleService articleService;

	public CommentServiceImpl(CommentRepository commentRepository, UserService userService,
		ArticleService articleService) {
		this.commentRepository = commentRepository;
		this.userService = userService;
		this.articleService = articleService;
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

		Article article = articleService.findByArticleId(commentCreateRequest.getArticle().getId());

		User user = userService.getUserFromAuth();

		Comment comment = CommentMapper.INSTANCE.commentCreateRequestToComment(commentCreateRequest);

		comment.addSubObject(user,article);

		Comment savedComment = commentRepository.save(comment);

		CommentReadResponse createdComment = CommentMapper.INSTANCE.commentToCommentReadResponse(savedComment);

		return createdComment;
	}

	@Override
	@Transactional
	public boolean removeComment(Long id) {

		Comment comment = commentRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("해당 댓글을 찾을 수 없습니다"));

		if(comment.getUser().getId() != userService.getUserFromAuth().getId()){
			throw new IllegalArgumentException("잘못된 요청입니다");
		}

		comment.deleteComment(); // 댓글 삭제

		commentRepository.save(comment);
		return true;
	}

	@Override
	@Transactional(readOnly = true)
	public List<CommentReadResponse> findCommentListByUser() {
		User user = userService.getUserFromAuth();
		List<Comment> commentList = commentRepository.findCommentsByUserAndIsDeletedFalse(user);

		List<CommentReadResponse> commentReadResponseList = commentList.stream().map(
			CommentMapper.INSTANCE::commentToCommentReadResponse
		).collect(Collectors.toList());

		return commentReadResponseList;
	}

}
