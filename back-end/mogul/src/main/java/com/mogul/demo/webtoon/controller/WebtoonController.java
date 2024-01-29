package com.mogul.demo.webtoon.controller;

import com.mogul.demo.util.CustomResponse;
import com.mogul.demo.library.service.LibraryService;
import com.mogul.demo.review.service.ReviewService;
import com.mogul.demo.webtoon.service.WebtoonService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


@RestController
@RequestMapping("/webtoon")
@RequiredArgsConstructor
@Tag(name = "Webtoon", description = "웹툰 API")
public class WebtoonController {

    private final WebtoonService webtoonService;

    private final ReviewService reviewService;

    private final LibraryService libraryService;

    @GetMapping
    @Operation(summary = "웹툰 메인 페이지 정보 조회", description = "웹툰 탭을 눌렀을 때 보여질 정보를 조회 합니다.", responses = {
            @ApiResponse(responseCode = "200", description = "죄회 성공")
    }, parameters = {
            @Parameter(name = "pno", description = "조회할 인기 웹툰의 페이지 번호 0번 부터 시작"),
            @Parameter(name = "count", description = "조회할 인기 웹툰의 한 페이지 크기")
    })
    public ResponseEntity<CustomResponse> webtoonListMain(@RequestParam("pno") int pageNumber, @RequestParam("count") int pageSize){
        Map<String, List> data = new HashMap<>();
        data.put("webtoon-top-grade", webtoonService.findWebtoonOrderByGrade(pageNumber, pageSize));
        data.put("webtoon-top-library", webtoonService.findWebtoonOrderByLibraryCount(pageNumber, pageSize));
        CustomResponse<Map> res = new CustomResponse<>(200, data, "웹툰 목록(평점순, 서재에 많이 담긴순) 데이터 읽기 성공");
        return new ResponseEntity<CustomResponse>(res, HttpStatus.OK);
    }

    @GetMapping("/all")
    @Operation(summary = "모든 웹툰 조회", description = "웹툰을 모두 조회합니다. 제목 순", responses = {
            @ApiResponse(responseCode = "200", description = "조회 성공")
    }, parameters = {
            @Parameter(name = "pno", description = "조회할 모든 웹툰의 페이지 번호 0번 부터 시작"),
            @Parameter(name = "count", description = "조회할 모든 웹툰의 한 페이지 크기")
    })
    public ResponseEntity<CustomResponse> webtoonListAll(@RequestParam("pno") int pageNumber, @RequestParam("count") int pageSize){
        List data = webtoonService.findWebtoonAll(pageNumber, pageSize);
        CustomResponse<List> res = new CustomResponse<>(200, data, "웹툰 목록(제목 순) 데이터 읽기 성공");
        return new ResponseEntity<CustomResponse>(res, HttpStatus.OK);
    }

    @GetMapping("/all/{genre}")
    @Operation(summary = "장르별 웹툰 보기 api", description = "장르명으로 웹툰을 조회합니다.", responses = {
            @ApiResponse(responseCode = "200", description = "조회 성공")
    }, parameters = {
            @Parameter(name = "genre", description = "장르명 DB에 동일한 장르명이 존재해야 함"),
            @Parameter(name = "pno", description = "조회할 장르별 웹툰의 페이지 번호 0번 부터 시작"),
            @Parameter(name = "count", description = "조회할 장르별 웹툰의 한 페이지 크기")
    })
    public ResponseEntity<CustomResponse> webtoonListGenre(@PathVariable("genre") String genre, @RequestParam("pno") int pageNumber, @RequestParam("count") int pageSize){
        List data = webtoonService.findWebtoonAllByGenre(genre, pageNumber, pageSize);
        CustomResponse<List> res = new CustomResponse<>(200, data, "웹툰 목록(장르별, 제목 순) 데이터 읽기 성공");
        return new ResponseEntity<CustomResponse>(res, HttpStatus.OK);
    }

    @GetMapping("/{webtoon-id}")
    @Operation(summary = "웹툰 상세 조회", description = "웹툰의 id로 웹툰을 상세 조회합니다.", responses = {
            @ApiResponse(responseCode = "200", description = "조회 성공")
    }, parameters = {
            @Parameter(name = "webtoonId", description = "조회할 웹툰의 id"),
            @Parameter(name = "pno", description = "관련된 리뷰와 서재의 페이지 번호 0번 부터 시작"),
            @Parameter(name = "count", description = "관련된 리뷰와 서재의 한 페이지 크기")
    })
    public ResponseEntity<CustomResponse> webtoonDetails(@PathVariable("webtoon-id") long webtoonId, @RequestParam("pno") int pageNumber, @RequestParam("count") int pageSize){
        Map<String, Object> data = new HashMap<>();
        data.put("webtoon_detail", webtoonService.findWebtoonById(webtoonId));
        data.put("reviews", reviewService.findReviewsByWebtoonId(webtoonId, pageNumber, pageSize));
        data.put("libraries", libraryService.findLibrariesByWebtoonId(webtoonId, pageNumber, pageSize));
        CustomResponse<Map> res = new CustomResponse<>(200, data, "웹툰 세부 정보와 관련 리뷰, 서재 읽기 성공");
        return new ResponseEntity<CustomResponse>(res, HttpStatus.OK);
    }


}
