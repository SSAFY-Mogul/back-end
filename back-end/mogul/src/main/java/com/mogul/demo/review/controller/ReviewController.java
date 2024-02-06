package com.mogul.demo.review.controller;

import com.mogul.demo.review.dto.ReviewAddRequest;
import com.mogul.demo.review.dto.ReviewUpdateRequest;
import com.mogul.demo.review.service.ReviewService;
import com.mogul.demo.user.entity.User;
import com.mogul.demo.user.service.UserService;
import com.mogul.demo.util.CustomResponse;
import com.mogul.demo.webtoon.service.WebtoonService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/review")
@Tag(name = "Review", description = "리뷰 API")
public class ReviewController {

    private final ReviewService reviewService;

    private final WebtoonService webtoonService;

    private final UserService userService;

    @PostMapping("/{webtoon-id}")
    @Operation(summary = "리뷰 Create API", description = "리뷰를 등록하고 등록 성고 여부를 반환 합니다.", responses = {
            @ApiResponse(responseCode = "200", description = "등록 성공"),
            @ApiResponse(responseCode = "404", description = "존재하지 않는 웹툰, 등록 실패"),
            @ApiResponse(responseCode = "400", description = "잘못된 요청 형식")
    }, parameters = {
            @Parameter(name = "webtoon-id", description = "리뷰를 등록하고자 하는 웹툰의 id"),
            @Parameter(name = "reviewAddRequest", description = "등록하고자 하는 리뷰 내용", content = {@Content(schema = @Schema(implementation = ReviewAddRequest.class))})
    })
    public ResponseEntity<CustomResponse> ReviewAdd(@PathVariable("webtoon-id") Long webtoonId, @RequestBody ReviewAddRequest reviewAddRequest, BindingResult bindingResult){
        CustomResponse res;
        if(bindingResult.hasErrors()){
            res = new CustomResponse(400, null, "잘못된 요청 형식입니다:");
        }else{
            if(webtoonService.isExist(webtoonId)) {
                User user = userService.getUserFromAuth();
                Long userId = user.getId();
                reviewAddRequest.setUserId(userId);
                reviewAddRequest.setWebtoonId(webtoonId);
                boolean data = reviewService.addReview(reviewAddRequest);
                res = new CustomResponse<Boolean>(data?200:400, data, data?"리뷰 등록 성공":"리뷰 등록 실패");
            }else{
                res = new CustomResponse(404, null, "존재하지 않는 웹툰입니다.");
            }
        }
        return new ResponseEntity<CustomResponse>(res, HttpStatus.OK);
    }

    @GetMapping("/{webtoon-id}")
    @Operation(summary = "리뷰 Read API", description = "웹툰 id로 특정 웹툰의 관련 리뷰를 조회합니다.", responses = {
            @ApiResponse(responseCode = "200", description = "조회 성공")
    }, parameters = {
            @Parameter(name = "webtoon-id", description = "조회할 리뷰들의 웹툰 id"),
            @Parameter(name = "pno", description = "조회할 리뷰의 페이지 번호"),
            @Parameter(name = "count", description = "조회할 리뷰의 페이지 크기")
    })
    public ResponseEntity<CustomResponse> reviewList(@PathVariable("webtoon-id") Long webtoonId, @RequestParam("pno") int pageNumber, @RequestParam("count") int pageSize){
        List data = reviewService.findReviewsByWebtoonId(webtoonId, pageNumber, pageSize);
        CustomResponse res = new CustomResponse<List>(200, data, "리뷰 조회 성공");
        return new ResponseEntity<CustomResponse>(res, HttpStatus.OK);
    }

    @PatchMapping("/{review-id}")
    @Operation(summary = "리뷰 Update API", description = "특정 리뷰의 내용을 수정 합니다.", responses = {
            @ApiResponse(responseCode = "200", description = "수정 성공"),
            @ApiResponse(responseCode = "404", description = "수정 실패"),
            @ApiResponse(responseCode = "400", description = "잘못된 요청 형식")
    }, parameters = {
            @Parameter(name = "review-id", description = "수정할 리뷰의 id"),
            @Parameter(name = "reviewUpdateRequest", description = "수정할 리뷰의 수정된 내용", content = {@Content(schema = @Schema(implementation = ReviewUpdateRequest.class))})
    })
    public ResponseEntity<CustomResponse> reviewModify(@PathVariable("review-id") Long id, @RequestBody @Valid ReviewUpdateRequest reviewUpdateRequest, BindingResult bindingResult){
        CustomResponse res;
        if(bindingResult.hasErrors()){
            res = new CustomResponse(400, null, "잘못된 요청 형식입니다:");
        }else{
            reviewUpdateRequest.setId(id);
            boolean data = reviewService.modifyReview(reviewUpdateRequest);
            res = new CustomResponse<Boolean>(data?200:404, data, data?"리뷰 수정 성공":"리뷰 수정 실패");
        }
        return new ResponseEntity<CustomResponse>(res, HttpStatus.OK);
    }

    @DeleteMapping("/{review-id}")
    @Operation(summary = "리뷰 Delete API", description = "특정 리뷰를 삭제 합니다.", responses = {
            @ApiResponse(responseCode = "200", description = "삭제 성공"),
            @ApiResponse(responseCode = "404", description = "리뷰를 찾을 수 없습니다, 삭제 실패")
    }, parameters = {
            @Parameter(name = "review-id", description = "삭제할 리뷰의 id")
    })
    public ResponseEntity<CustomResponse> reviewRemove(@PathVariable("review-id") Long id){
        boolean data = reviewService.removeReview(id);
        CustomResponse res = new CustomResponse<Boolean>(data?200:404, data, data?"리뷰 삭제 성공":"리뷰 삭제 실패");
        return new ResponseEntity<CustomResponse>(res, HttpStatus.OK);
    }

}
