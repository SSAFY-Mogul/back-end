package com.mogul.demo.user.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import com.mogul.demo.user.dto.UserRegistrationRequest;
import com.mogul.demo.user.dto.UserRegistrationResponse;
import com.mogul.demo.user.entity.User;

@Mapper
public interface UserMapper {
	UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);
	User userRegistrationRequestToUser(UserRegistrationRequest userRegistrationRequest);
	UserRegistrationResponse userTouserRegistrationResponse(User user);
}