package com.mogul.demo.chat.dto;

import lombok.*;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode
public class ChatHistoryResponse {
    private String writer;
    private String message;
    private Date registeredDate;
}
