package com.mogul.demo.user.service;

import com.mogul.demo.user.dto.UserDto;
import com.mogul.demo.user.dto.UserInfoReadResponse;
import com.mogul.demo.user.dto.UserInfoSetRequest;

public interface ProfileService {
	//내가 쓴 리뷰

	UserInfoReadResponse getUserInfoById(Long id);

	UserDto setUserInfo(UserInfoSetRequest userInfoSetRequest);
}
