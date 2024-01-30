package com.mogul.demo.chat.mapper;

import com.mogul.demo.chat.document.ChatMessageDocument;
import com.mogul.demo.chat.dto.ChatHistoryResponse;
import org.mapstruct.Mapper;

@Mapper
public interface ChatHistoryMapper {

    public static ChatHistoryMapper INSTANCE = new ChatHistoryMapperImpl();


    ChatHistoryResponse fromChatMessageDocumentToChatistoryResponse(ChatMessageDocument chatMessageDocument);
}
