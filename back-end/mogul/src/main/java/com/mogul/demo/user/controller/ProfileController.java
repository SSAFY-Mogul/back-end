package com.mogul.demo.user.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mogul.demo.board.dto.ArticleReadResponse;
import com.mogul.demo.board.dto.CommentReadResponse;
import com.mogul.demo.board.service.ArticleService;
import com.mogul.demo.board.service.CommentService;
import com.mogul.demo.library.dto.LibraryResponse;
import com.mogul.demo.library.dto.SubscriptionResponse;
import com.mogul.demo.library.service.LibraryService;
import com.mogul.demo.review.dto.ReviewResponse;
import com.mogul.demo.review.service.ReviewService;
import com.mogul.demo.user.auth.util.AuthUtil;
import com.mogul.demo.user.dto.UserInfoReadResponse;
import com.mogul.demo.user.dto.UserInfoSetRequest;
import com.mogul.demo.user.exception.NoSuchUserException;
import com.mogul.demo.user.service.ProfileService;
import com.mogul.demo.util.CustomResponse;

import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RequestMapping("/api/user/profile/")
@RestController
public class ProfileController {
	private final ProfileService profileService;
	// private final ReviewService reviewService;
	private final LibraryService libraryService;
	// private final ArticleService articleService;
	// private final CommentService commentService;

	private static final int INFO_SIZE = 5;

	@GetMapping("/info")
	public ResponseEntity<CustomResponse<UserInfoReadResponse>> profile(
		//요청은 토큰만
		HttpServletResponse response
	) {
		Long id = AuthUtil.getAuthenticationInfoId();
		UserInfoReadResponse userInfoReadResponse = profileService.getUserInfoById(id);

		try {
			userInfoReadResponse = profileService.getUserInfoById(id);
		} catch (NoSuchUserException e) {
			return new ResponseEntity<>(
				new CustomResponse<>(
					HttpStatus.BAD_REQUEST.value(),
					null,
					e.getMessage()
				),
				HttpStatus.BAD_REQUEST
			);
		}

		return ResponseEntity.ok(
			new CustomResponse<>(
				HttpStatus.OK.value(),
				userInfoReadResponse,
				"사용자 정보를 불러왔습니다."
			)
		);
	}

	@PatchMapping("/info")
	public ResponseEntity<CustomResponse<String>> setProfile(
		//요청은 토큰만
		@RequestBody
		UserInfoSetRequest userInfoSetRequest,
		HttpServletResponse response
	) {
		profileService.setUserInfo(userInfoSetRequest);

		return ResponseEntity.ok(
			new CustomResponse<>(
				HttpStatus.OK.value(),
				"",
				"사용자 정보가 변경되었습니다."
			)
		);
	}

	@GetMapping("/library")
	public ResponseEntity<CustomResponse<List<LibraryResponse>>> myLibraries(
		//요청은 토큰
	) {
		Long userId = AuthUtil.getAuthenticationInfoId();
		List<LibraryResponse> libraries = libraryService.findLibrariesByUserId(userId);
		libraries = libraries.subList(0, Math.min());

		return ResponseEntity.ok(
			new CustomResponse<>(
				HttpStatus.OK.value(),
				libraries,
				"사용자 서재 정보를 불러왔습니다."
			)
		);
	}

	@GetMapping("/subscribed")
	public ResponseEntity<CustomResponse<List<SubscriptionResponse>>> subscriptions(
		//요청은 토큰
	) {
		Long userId = AuthUtil.getAuthenticationInfoId();
		List<SubscriptionResponse> subscriptions = libraryService.findSubscription(userId, 0, INFO_SIZE);

		return ResponseEntity.ok(
			new CustomResponse<>(
				HttpStatus.OK.value(),
				subscriptions,
				"사용자가 구독한 서재 정보를 불러왔습니다."
			)
		);
	}

	/*
	내가 쓴 게시글
	내가 쓴 댓글
	내가 쓴 리뷰
	 */
	// @GetMapping("/articles")
	// public ResponseEntity<CustomResponse<List<ArticleReadResponse>>> articles(
	// 	//요청은 토큰
	// ) {
	// 	List<ArticleReadResponse> articles = articleService.findArticleListByUser(1, INFO_SIZE);
	//
	// 	return ResponseEntity.ok(
	// 		new CustomResponse<>(
	// 			HttpStatus.OK.value(),
	// 			articles,
	// 			"사용자가 작성한 게시글 목록을 불러왔습니다."
	// 		)
	// 	);
	//
	// }
	//
	// @GetMapping("/comments")
	// public ResponseEntity<CustomResponse<List<CommentReadResponse>>> comments(
	// 	//요청은 토큰
	// ) {
	// 	List<CommentReadResponse> comments = commentService.findCommentListByUser();
	// 	comments = comments.subList(0, Math.min(comments.size(), INFO_SIZE));
	//
	// 	return ResponseEntity.ok(
	// 		new CustomResponse<>(
	// 			HttpStatus.OK.value(),
	// 			comments,
	// 			"사용자가 작성한 댓글 목록을 불러왔습니다."
	// 		)
	// 	);
	// }
	//
	// @GetMapping("/reviews")
	// public ResponseEntity<CustomResponse<List<ReviewResponse>>> reviews(
	// 	//요청은 토큰
	// ) {
	// 	List<ReviewResponse> reviews = null;
	// 	Long userId = AuthUtil.getAuthenticationInfoId();
	// 	reviews = reviewService.findReviewMy(userId, 0, INFO_SIZE);
	//
	// 	return ResponseEntity.ok(
	// 		new CustomResponse<>(
	// 			HttpStatus.OK.value(),
	// 			reviews,
	// 			"사용자가 작성한 리뷰 목록을 불러왔습니다."
	// 		)
	// 	);
	// }
}
