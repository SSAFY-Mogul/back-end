package com.mogul.demo.board.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.mogul.demo.board.entity.Comment;
import com.mogul.demo.user.entity.User;

public interface CommentRepository extends JpaRepository<Comment,Long> {
	@Query("SELECT c FROM Comment c WHERE c.group = 0 and c.article.id = :articleId ORDER BY c.id")
	List<Comment> findParentComments(@Param("articleId")Long articleId);
	// 부모댓글을 가져오는 쿼리

	@Query("SELECT c FROM Comment c WHERE c.group = :parentId ORDER BY c.id")
	List<Comment> findChildCommentsByParentId(@Param("parentId") Long parentId);
	// 자식댓글을 가져오는 쿼리

	Optional<Comment> findById(Long id);

	List<Comment> findCommentsByUserAndIsDeletedFalse(User user, Pageable pageable);
}
