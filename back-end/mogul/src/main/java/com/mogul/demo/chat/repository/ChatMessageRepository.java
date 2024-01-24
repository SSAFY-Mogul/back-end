package com.mogul.demo.chat.repository;

import com.mogul.demo.chat.document.ChatMessageDocument;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ChatMessageRepository extends MongoRepository<ChatMessageDocument, String> {
    List<ChatMessageDocument> findAllByChatRoomId(int chatRoomId);
}
