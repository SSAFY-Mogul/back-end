package com.mogul.demo.chat.repository;

import com.mogul.demo.chat.document.ChatMessageDocument;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ChatMessageRepository extends MongoRepository<ChatMessageDocument, String> {
    List<ChatMessageDocument> findAllByChatRoomId(int chatRoomId);
}
