package com.mogul.demo.chat.controller;

import com.mogul.demo.chat.service.ChatHistoryService;
import com.mogul.demo.util.CustomResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/api/history")
@RequiredArgsConstructor
@Tag(name = "Chatting", description = "이전 채팅 API")
public class ChatHistoryController {

    private final ChatHistoryService chatHistoryService;

    @GetMapping("/{webtoon-id}")
    @Operation(summary = "이전 채팅 조회 API", description = "특정 기간의 채팅 내역을 조회합니다. 현재 까지의 내용을 조회하려면 end를 내일날짜로 조회해 주세요.", responses = {
            @ApiResponse(responseCode = "200", description = "조회 성공")
    }, parameters = {
            @Parameter(name = "webtoon-id", description = "조회할 웹툰의 id(채팅방의 id)"),
            @Parameter(name = "start", description = "조회할 기간의 시작 날짜", content = {@Content(schema = @Schema(implementation = Date.class))}),
            @Parameter(name = "end", description = "조회할 기간의 끝 날짜", content = {@Content(schema = @Schema(implementation = Date.class))})
    })
    public ResponseEntity<CustomResponse> chatMessageList(@PathVariable("webtoon-id")Long webtoonId, @RequestParam("start") @DateTimeFormat(pattern="yyyy-MM-dd") Date startDate, @RequestParam("end") @DateTimeFormat(pattern="yyyy-MM-dd") Date endDate){
        CustomResponse res = new CustomResponse(200, chatHistoryService.findChatHistory(webtoonId, startDate, endDate), startDate+"~"+endDate+"의 채팅 기록");
        return new ResponseEntity<CustomResponse>(res, HttpStatus.OK);
    }
}
