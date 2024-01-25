package com.mogul.demo.chat.service;

import com.mogul.demo.chat.dto.ChatHistoryResponse;

import java.util.Date;
import java.util.List;

public interface ChatHistoryService {
    List<ChatHistoryResponse> findChatHistory(int webtoonId, Date startDate, Date endDate);
}
