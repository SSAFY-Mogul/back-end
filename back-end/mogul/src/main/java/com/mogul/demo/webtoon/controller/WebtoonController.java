package com.mogul.demo.webtoon.controller;

import com.mogul.demo.util.CustomResponse;
import com.mogul.demo.library.service.LibraryService;
import com.mogul.demo.review.service.ReviewService;
import com.mogul.demo.webtoon.service.WebtoonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/webtoon")
public class WebtoonController {

    @Autowired
    WebtoonService webtoonService;

    @Autowired
    ReviewService reviewService;

    @Autowired
    LibraryService libraryService;

    @GetMapping
    public ResponseEntity<CustomResponse> webtoonListMain(@RequestParam("pno") int pageNumber, @RequestParam("count") int pageSize){
        Map<String, List> data = new HashMap<>();
        data.put("webtoon-top-grade", webtoonService.findWebtoonOrderByGrade(pageNumber, pageSize));
        data.put("webtoon-top-library", webtoonService.findWebtoonOrderByLibraryCount(pageNumber, pageSize));
        CustomResponse<Map> res = new CustomResponse<>(200, data, "웹툰 목록(평점순, 서재에 많이 담긴순) 데이터 읽기 성공");
        return new ResponseEntity<CustomResponse>(res, HttpStatus.ACCEPTED);
    }

    @GetMapping("/all")
    public ResponseEntity<CustomResponse> webtoonListAll(@RequestParam("pno") int pageNumber, @RequestParam("count") int pageSize){
        List data = webtoonService.findWebtoonAll(pageNumber, pageSize);
        CustomResponse<List> res = new CustomResponse<>(200, data, "웹툰 목록(제목 순) 데이터 읽기 성공");
        return new ResponseEntity<CustomResponse>(res, HttpStatus.ACCEPTED);
    }

    @GetMapping("/all/{genre}")
    public ResponseEntity<CustomResponse> webtoonListGenre(@PathVariable("genre") String genre, @RequestParam("pno") int pageNumber, @RequestParam("count") int pageSize){
        List data = webtoonService.findWebtoonAllByGenre(genre, pageNumber, pageSize);
        CustomResponse<List> res = new CustomResponse<>(200, data, "웹툰 목록(장르별, 제목 순) 데이터 읽기 성공");
        return new ResponseEntity<CustomResponse>(res, HttpStatus.ACCEPTED);
    }

    @GetMapping("/{webtoon-id}")
    public ResponseEntity<CustomResponse> webtoonDetails(@PathVariable("webtoon-id") long webtoonId, @RequestParam("pno") int pageNumber, @RequestParam("count") int pageSize){
        Map<String, Object> data = new HashMap<>();
        data.put("webtoon_detail", webtoonService.findWebtoonById(webtoonId));
        data.put("reviews", reviewService.findReviewsByWebtoonId(webtoonId, pageNumber, pageSize));
        data.put("libraries", libraryService.findLibrariesByWebtoonId(webtoonId, pageNumber, pageSize));
        CustomResponse<Map> res = new CustomResponse<>(200, data, "웹툰 세부 정보와 관련 리뷰, 서재 읽기 성공");
        return new ResponseEntity<CustomResponse>(res, HttpStatus.ACCEPTED);
    }


}
