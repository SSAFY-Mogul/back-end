package com.mogul.demo.review.controller;

import com.mogul.demo.review.dto.ReviewAddRequest;
import com.mogul.demo.review.service.ReviewService;
import com.mogul.demo.util.CustomResponse;
import com.mogul.demo.webtoon.service.WebtoonService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/review")
public class ReviewController {

    private final ReviewService reviewService;

    private final WebtoonService webtoonService;

    @PostMapping("/{webtoon-id}")
    public ResponseEntity<CustomResponse> ReviewAdd(@PathVariable("webtoon-id") long webtoonId, @RequestBody ReviewAddRequest reviewAddRequest, BindingResult bindingResult){
        CustomResponse res;
        if(bindingResult.hasErrors()){
            res = new CustomResponse(400, null, "잘못된 요청 형식입니다:");
        }else{
            if(webtoonService.isExist(webtoonId)) {
                long userId = 1; // 로그인 구현 후 변경 요망!!!!!!
                reviewAddRequest.setUserId(userId);
                reviewAddRequest.setWebtoonId(webtoonId);
                boolean data = reviewService.addReview(reviewAddRequest);
                if(data){
                    float drawingGrade = reviewService.findDrawingGrade(webtoonId);
                    float storyGrade = reviewService.findStoryGrade(webtoonId);
                    float directingGrade = reviewService.findDirectingGrade(webtoonId);
                    float grade = (drawingGrade + storyGrade + directingGrade) / 3.0f;
                    webtoonService.modifyWebtoonGrade(webtoonId, grade, drawingGrade, storyGrade, directingGrade);
                }
                res = new CustomResponse<Boolean>(data?200:400, data, data?"리뷰 등록 성공":"리뷰 등록 실패");
            }else{
                res = new CustomResponse(404, null, "존재하지 않는 웹툰입니다.");
            }
        }
        return new ResponseEntity<CustomResponse>(res, HttpStatus.OK);
    }

    @GetMapping("/{webtoon-id}")
    public ResponseEntity<CustomResponse> ReviewList(@PathVariable("webtoon-id") long webtoonId, @RequestParam("pno") int pageNumber, @RequestParam("count") int pageSize){
        List data = reviewService.findReviewsByWebtoonId(webtoonId, pageNumber, pageSize);
        CustomResponse res = new CustomResponse<List>(200, data, "리뷰 조회 성공");
        return new ResponseEntity<CustomResponse>(res, HttpStatus.OK);
    }
}
