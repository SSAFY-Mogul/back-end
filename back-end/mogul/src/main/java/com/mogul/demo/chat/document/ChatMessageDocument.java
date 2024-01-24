package com.mogul.demo.chat.document;

import lombok.*;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode
@Document(collection = "chat")
public class ChatMessageDocument {
    private String writer;
    private String message;
    private int chatRoomId;
    private Date registeredDate;

    public ChatMessageDocument(ChatMessageDocument chatMessageDocument){
        this.writer = chatMessageDocument.getWriter();
        this.message = chatMessageDocument.getMessage();
        this.chatRoomId = chatMessageDocument.getChatRoomId();
        this.registeredDate = chatMessageDocument.getRegisteredDate();
    }
}
