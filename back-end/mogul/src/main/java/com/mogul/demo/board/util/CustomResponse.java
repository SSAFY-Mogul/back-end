package com.mogul.demo.board.util;

import org.springframework.http.HttpStatus;

import lombok.Data;

@Data
public class CustomResponse<T> {
	private int status;
	private String message;
	private T data;

	public CustomResponse(T data){
		this.status = HttpStatus.OK.value();
		this.data = data;
	}
	public CustomResponse(int status,T data,String message){
		this.status = status;
		this.data = data;
		this.message = message;
	}


}
