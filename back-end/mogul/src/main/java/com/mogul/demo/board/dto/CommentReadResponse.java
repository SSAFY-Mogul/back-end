package com.mogul.demo.board.dto;

import com.mogul.demo.user.dto.UserResponse;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "Comment Read Response")
public class CommentReadResponse extends CommentResponse{
	private UserResponse user;
}
