package com.mogul.demo.webtoon.controller;

import com.mogul.demo.common.dto.WebtoonDetailCommonResponse;
import com.mogul.demo.common.dto.WebtoonMainResponse;
import com.mogul.demo.common.service.CommonWebtoonService;
import com.mogul.demo.library.dto.LibraryResponse;
import com.mogul.demo.review.dto.ReviewResponse;
import com.mogul.demo.user.service.UserService;
import com.mogul.demo.util.CustomResponse;
import com.mogul.demo.library.service.LibraryService;
import com.mogul.demo.review.service.ReviewService;
import com.mogul.demo.webtoon.dto.WebtoonLikeResponse;
import com.mogul.demo.webtoon.dto.WebtoonResponse;
import com.mogul.demo.webtoon.dto.WebtoonTagResponse;
import com.mogul.demo.webtoon.service.WebtoonLikeService;
import com.mogul.demo.webtoon.service.WebtoonService;
import com.mogul.demo.webtoon.service.WebtoonTagService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/webtoon")
@RequiredArgsConstructor
@Tag(name = "Webtoon", description = "웹툰 API")
public class WebtoonController {

    private final WebtoonService webtoonService;

    private final ReviewService reviewService;

    private final LibraryService libraryService;

    private final WebtoonTagService webtoonTagService;

    private final WebtoonLikeService webtoonLikeService;

    private final UserService userService;

    private final CommonWebtoonService commonWebtoonService;

    @GetMapping
    @Operation(summary = "웹툰 메인 페이지 정보 조회", description = "웹툰 탭을 눌렀을 때 보여질 정보를 조회 합니다.", responses = {
            @ApiResponse(responseCode = "200", description = "죄회 성공", content = {@Content(schema = @Schema(implementation = WebtoonResponse.class))})
    }, parameters = {
            @Parameter(name = "pno", description = "조회할 인기 웹툰의 페이지 번호 0번 부터 시작"),
            @Parameter(name = "count", description = "조회할 인기 웹툰의 한 페이지 크기")
    })
    public ResponseEntity<CustomResponse> webtoonListMain(@RequestParam("pno") int pageNumber, @RequestParam("count") int pageSize){
        WebtoonMainResponse data = commonWebtoonService.listWebtoonMain(pageNumber, pageSize);
        CustomResponse res = new CustomResponse<WebtoonMainResponse>(200, data, "웹툰 목록(평점순, 서재에 많이 담긴순) 데이터 읽기 성공");
        return new ResponseEntity<CustomResponse>(res, HttpStatus.OK);
    }

    @GetMapping("/all")
    @Operation(summary = "모든 웹툰 조회", description = "웹툰을 모두 조회합니다. 제목 순", responses = {
            @ApiResponse(responseCode = "200", description = "조회 성공", content = {@Content(schema = @Schema(implementation = WebtoonResponse.class))})
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
            @ApiResponse(responseCode = "200", description = "조회 성공", content = {@Content(schema = @Schema(implementation = WebtoonResponse.class))})
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
            @ApiResponse(responseCode = "200", description = "조회 성공", content = {
                    @Content(schema = @Schema(implementation = WebtoonResponse.class)),
                    @Content(schema = @Schema(implementation = ReviewResponse.class)),
                    @Content(schema = @Schema(implementation = LibraryResponse.class))
            })
    }, parameters = {
            @Parameter(name = "webtoon-id", description = "조회할 웹툰의 id"),
            @Parameter(name = "pno", description = "관련된 리뷰와 서재의 페이지 번호 0번 부터 시작"),
            @Parameter(name = "count", description = "관련된 리뷰와 서재의 한 페이지 크기")
    })
    public ResponseEntity<CustomResponse> webtoonDetails(@PathVariable("webtoon-id") Long webtoonId, @RequestParam("pno") int pageNumber, @RequestParam("count") int pageSize){
        WebtoonDetailCommonResponse data = commonWebtoonService.getWebtoonDetail(webtoonId, pageNumber, pageSize);
        CustomResponse res = new CustomResponse<WebtoonDetailCommonResponse>(200, data, "웹툰 세부 정보와 관련 리뷰, 서재 읽기 성공");
        return new ResponseEntity<CustomResponse>(res, HttpStatus.OK);
    }

    @GetMapping("/{webtoon-id}/tag")
    @Operation(summary = "태그 Read API", description = "특정 웹툰에 달린 태그를 조회합니다.", responses = {
        @ApiResponse(responseCode = "200", description = "조회 성공", content = {
                @Content(schema = @Schema(implementation = WebtoonTagResponse.class))
        })
    }, parameters = {
            @Parameter(name = "webtoon-id", description = "태그를 조회할 웹툰 id")
    })
    public ResponseEntity<CustomResponse> tagList(@PathVariable("webtoon-id") Long webtoonId){
        List data = webtoonTagService.findTag(webtoonId);
        CustomResponse res = new CustomResponse<List>(200, data, "웹툰 관련 태그 조회 성공");
        return new ResponseEntity<CustomResponse>(res, HttpStatus.OK);
    }

    @GetMapping("/tag-search/{tag-id}")
    public ResponseEntity<CustomResponse> tagSearch(@PathVariable("tag-id") Long tagId){
        List data = webtoonTagService.findWebtoonByTagId(tagId);
        CustomResponse res = new CustomResponse<List>(200, data, "태그로 웹툰 검색 성공");
        return new ResponseEntity<CustomResponse>(res, HttpStatus.OK);
    }

    @GetMapping("/{webtoon-id}/like")
    public ResponseEntity<CustomResponse> likeGet(@PathVariable("webtoon-id") Long webtoonId){
        WebtoonLikeResponse data = commonWebtoonService.getLike(webtoonId);
        CustomResponse res = new CustomResponse<WebtoonLikeResponse>(200, data, "좋아요 조회 성공");
        return new ResponseEntity<CustomResponse>(res, HttpStatus.OK);
    }

    @PostMapping("/{webtoon-id}/like")
    public ResponseEntity<CustomResponse> likeAdd(@PathVariable("webtoon-id") Long webtoonId){
        boolean data = commonWebtoonService.addLike(webtoonId);
        CustomResponse res = new CustomResponse<Boolean>(data?200:400, data, data?"좋아요 등록 성공":"좋아요 등록 실패");
        return new ResponseEntity<CustomResponse>(res, HttpStatus.OK);
    }

    @DeleteMapping("/{webtoon-id}/like")
    public ResponseEntity<CustomResponse> likeRemove(@PathVariable("webtoon-id") Long webtoonId){
        boolean data = commonWebtoonService.removeLike(webtoonId);
        CustomResponse res = new CustomResponse<Boolean>(data?200:400, data, data?"좋아요 삭제 성공":"좋아요 삭제 실패");
        return new ResponseEntity<CustomResponse>(res, HttpStatus.OK);
    }
}
