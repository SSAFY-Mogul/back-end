package com.mogul.demo.review.controller;

import com.mogul.demo.review.dto.ReviewAddRequest;
import com.mogul.demo.review.dto.ReviewUpdateRequest;
import com.mogul.demo.review.service.ReviewService;
import com.mogul.demo.util.CustomResponse;
import com.mogul.demo.webtoon.service.WebtoonService;
import jakarta.validation.Valid;
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
                res = new CustomResponse<Boolean>(data?200:400, data, data?"리뷰 등록 성공":"리뷰 등록 실패");
            }else{
                res = new CustomResponse(404, null, "존재하지 않는 웹툰입니다.");
            }
        }
        return new ResponseEntity<CustomResponse>(res, HttpStatus.OK);
    }

    @GetMapping("/{webtoon-id}")
    public ResponseEntity<CustomResponse> reviewList(@PathVariable("webtoon-id") long webtoonId, @RequestParam("pno") int pageNumber, @RequestParam("count") int pageSize){
        List data = reviewService.findReviewsByWebtoonId(webtoonId, pageNumber, pageSize);
        CustomResponse res = new CustomResponse<List>(200, data, "리뷰 조회 성공");
        return new ResponseEntity<CustomResponse>(res, HttpStatus.OK);
    }

    @PatchMapping("/{review-id}")
    public ResponseEntity<CustomResponse> reviewModify(@PathVariable("review-id") long id, @RequestBody @Valid ReviewUpdateRequest reviewUpdateRequest, BindingResult bindingResult){
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
    public ResponseEntity<CustomResponse> reviewRemove(@PathVariable("review-id") long id){
        boolean data = reviewService.removeReview(id);
        CustomResponse res = new CustomResponse<Boolean>(data?200:404, data, data?"리뷰 삭제 성공":"리뷰 삭제 실패");
        return new ResponseEntity<CustomResponse>(res, HttpStatus.OK);
    }

}
