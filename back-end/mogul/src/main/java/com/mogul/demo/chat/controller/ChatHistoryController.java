package com.mogul.demo.chat.controller;

import com.mogul.demo.chat.service.ChatHistoryService;
import com.mogul.demo.util.CustomResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/history")
public class ChatHistoryController {

    @Autowired
    ChatHistoryService chatHistoryService;

    @GetMapping("/{webtoon-id}")
    public ResponseEntity<CustomResponse> chatMessageList(@PathVariable("webtoon-id")int webtoonId, @RequestParam("start") @DateTimeFormat(pattern="yyyy-MM-dd") Date startDate, @RequestParam("end") @DateTimeFormat(pattern="yyyy-MM-dd") Date endDate){
        CustomResponse res = new CustomResponse(200, chatHistoryService.findChatHistory(webtoonId, startDate, endDate), startDate+"~"+endDate+"의 채팅 기록");
        return new ResponseEntity<CustomResponse>(res, HttpStatus.ACCEPTED);
    }
}
