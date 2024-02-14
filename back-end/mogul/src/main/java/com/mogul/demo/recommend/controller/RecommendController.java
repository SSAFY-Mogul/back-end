package com.mogul.demo.recommend.controller;

import com.mogul.demo.common.service.CommonRecommendService;
import com.mogul.demo.util.CustomResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/recommend")
@RequiredArgsConstructor
public class RecommendController {

    private final CommonRecommendService commonRecommendService;

    @GetMapping
    public ResponseEntity<CustomResponse> RecommandList(){
        List data = commonRecommendService.listRecommandWebtoons();
        CustomResponse res = new CustomResponse<List>(200, data, "추천 웹툰 조회 성공");
        return new ResponseEntity<CustomResponse>(res, HttpStatus.OK);
    }
}
