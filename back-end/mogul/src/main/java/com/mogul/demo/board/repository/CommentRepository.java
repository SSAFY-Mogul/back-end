package com.mogul.demo.board.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.mogul.demo.board.entity.Comment;

public interface CommentRepository extends JpaRepository<Comment,Integer> {
	@Query("SELECT c FROM Comment c WHERE c.group = 0 ORDER BY c.id")
	List<Comment> findParentComments();
	// 부모댓글을 가져오는 쿼리

	@Query("SELECT c FROM Comment c WHERE c.group = :parentId ORDER BY c.id")
	List<Comment> findChildCommentsByParentId(@Param("parentId") int parentId);
	// 자식댓글을 가져오는 쿼리

}
