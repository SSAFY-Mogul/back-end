package com.mogul.demo.chat.repository;

import com.mogul.demo.chat.document.ChatMessageDocument;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Repository
public interface ChatMessageRepository extends MongoRepository<ChatMessageDocument, String> {
    List<ChatMessageDocument> findByWebtoonIdAndRegisteredDateBetween(Long webtoonId, Date startDate, Date endDate);
}
