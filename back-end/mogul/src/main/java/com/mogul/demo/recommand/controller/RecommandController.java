package com.mogul.demo.recommand.controller;

import com.mogul.demo.common.service.CommonRecommandService;
import com.mogul.demo.util.CustomResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/recommand")
@RequiredArgsConstructor
public class RecommandController {

    private final CommonRecommandService commonRecommandService;

    @GetMapping
    public ResponseEntity<CustomResponse> RecommandList(){
        List data = commonRecommandService.listRecommandWebtoons();
        CustomResponse res = new CustomResponse<List>(200, data, "추천 웹툰 조회 성공");
        return new ResponseEntity<CustomResponse>(res, HttpStatus.OK);
    }
}
