package com.mogul.demo.chat.service;

import com.mogul.demo.chat.dto.ChatHistoryResponse;
import com.mogul.demo.chat.mapper.ChatHistoryMapper;
import com.mogul.demo.chat.repository.ChatMessageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ChatHistoryServiceImpl implements ChatHistoryService {

    private final ChatMessageRepository chatMessageRepository;

    @Override
    public List<ChatHistoryResponse> findChatHistory(int webtoonId, Date startDate, Date endDate) {
        return chatMessageRepository.findByWebtoonIdAndRegisteredDateBetween(webtoonId, startDate, endDate).stream().map(ChatHistoryMapper.INSTANCE::fromChatMessageDocumentToChatistoryResponse).collect(Collectors.toList());
    }
}
