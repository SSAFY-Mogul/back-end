package com.mogul.demo.admin.controller;

import com.mogul.demo.admin.dto.WebtoonAddRequest;
import com.mogul.demo.admin.dto.WebtoonTagAddRequest;
import com.mogul.demo.util.CustomResponse;
import com.mogul.demo.webtoon.service.WebtoonService;
import com.mogul.demo.webtoon.service.WebtoonTagService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin") // 어드민 테스트용 url
@RequiredArgsConstructor
public class AdminController {

    private final WebtoonService webtoonService;

    private final WebtoonTagService webtoonTagService;

    @PostMapping("/webtoon")
    public ResponseEntity<CustomResponse> webtoonAdd(@RequestBody @Valid WebtoonAddRequest webtoonAddRequest){
        boolean data = webtoonService.addWebtoon(webtoonAddRequest);
        CustomResponse res = new CustomResponse<Boolean>(data?200:500, data, data?"웹툰 등록 성공":"웹툰 등록 실패");
        return new ResponseEntity<CustomResponse>(res, HttpStatus.OK);
    }

    @PostMapping("/webtoon/tag")
    public ResponseEntity<CustomResponse> webtoonTagAdd(@RequestBody @Valid WebtoonTagAddRequest webtoonTagAddRequest){
        boolean data = webtoonTagService.addTag(webtoonTagAddRequest);
        CustomResponse res = new CustomResponse<Boolean>(data?200:500, data, data?"웹툰 태그 등록 성공":"웹툰  태그 등록 실패");
        return new ResponseEntity<CustomResponse>(res, HttpStatus.OK);
    }

    @DeleteMapping("/webtoon/{webtoon-id}")
    public ResponseEntity<CustomResponse> webtoonRemove(@PathVariable Long webtoonId){
        boolean data = webtoonService.removeWebtoon(webtoonId);
        CustomResponse res = new CustomResponse<Boolean>(data?200:404, data, data?"웹툰 삭제 성공":"웹툰 삭제 실패");
        return new ResponseEntity<CustomResponse>(res, HttpStatus.OK);
    }

    @DeleteMapping("/webtoon/tag/{tag-id}")
    public ResponseEntity<CustomResponse> webtoonTagRemove(@PathVariable Long tagId){
        boolean data = webtoonTagService.removeWebtoonTag(tagId);
        CustomResponse res = new CustomResponse<Boolean>(data?200:404, data, data?"웹툰 태그 삭제 성공":"웹툰 태그 삭제 실패");
        return new ResponseEntity<CustomResponse>(res, HttpStatus.OK);
    }

    @GetMapping("/webtoon/tag")
    public ResponseEntity<CustomResponse> webtoonTagList(){
        List data = webtoonTagService.findWebtoonTag();
        CustomResponse res = new CustomResponse<List>(200, data, "웹툰 태그 목록 조회 성공");
        return new ResponseEntity<CustomResponse>(res, HttpStatus.OK);
    }
}
