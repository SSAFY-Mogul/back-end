package com.mogul.demo.util;

import lombok.*;
import org.springframework.http.HttpStatus;

import java.util.List;
import java.util.Map;


@Getter
@Setter
@NoArgsConstructor
@ToString
@EqualsAndHashCode
public class CustomResponse <T>{
    private int status;
    private String message;
    private T data;

    public CustomResponse(T data){
        this.status = HttpStatus.OK.value();
        this.data = data;
    }
    public CustomResponse(int status,T data,String message) {
        this.status = status;
        this.data = data;
        this.message = message;
    }
}
