package com.mogul.demo.user.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.mogul.demo.board.dto.ArticleCreateRequest;
import com.mogul.demo.board.entity.Article;
import com.mogul.demo.user.dto.UserJoinRequest;
import com.mogul.demo.user.entity.User;

@Mapper
public interface UserMapper {

	@Mapping(target = "userId", source = "userId")
	User userJoinRequestToUser(UserJoinRequest userJoinRequest);
}
