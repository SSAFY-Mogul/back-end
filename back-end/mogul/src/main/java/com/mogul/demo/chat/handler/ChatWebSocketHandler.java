package com.mogul.demo.chat.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mogul.demo.chat.dto.NicknameResponse;
import com.mogul.demo.chat.interceptor.ChatHandShakeInterceptor;
import com.mogul.demo.chat.nickname.NicknameGenerator;
import com.mogul.demo.util.CustomResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.util.*;

@Component
public class ChatWebSocketHandler extends TextWebSocketHandler {

    Map<Integer, List<WebSocketSession>> chatRoom = new HashMap<>();

    ObjectMapper objectMapper = new ObjectMapper();

    @Autowired
    NicknameGenerator nicknameGenerator;

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        int chatRoomId = (int) session.getAttributes().get("chat-room-id");
        if(!chatRoom.containsKey(chatRoomId)){
            chatRoom.put(chatRoomId, new ArrayList<WebSocketSession>());
        }
        chatRoom.get(chatRoomId).add(session);
        String nickname = nicknameGenerator.generateNickname(chatRoomId);
        session.getAttributes().put("nickname", nickname);
        session.sendMessage(new TextMessage(objectMapper.writeValueAsString(nickname)));
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        int chatRoomId = (int) session.getAttributes().get("chat-room-id");
        if(!chatRoom.containsKey(chatRoomId)) {
            chatRoom.put(chatRoomId, new ArrayList<WebSocketSession>());
            chatRoom.get(chatRoomId).add(session);
        }

        StringBuilder payload = new StringBuilder();
        payload.append(session.getId()).append(":").append(message.getPayload());

        for(WebSocketSession wss : chatRoom.get(chatRoomId)){
            wss.sendMessage(new TextMessage(payload.toString()));
        }
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        int chatRoomId = (int) session.getAttributes().get("chat-room-id");
        if(chatRoom.containsKey(chatRoomId)) {
            chatRoom.get(chatRoomId).remove(session);
            if(chatRoom.get(chatRoomId).isEmpty()) {
                chatRoom.remove(chatRoomId);
            }
        }
    }
}
