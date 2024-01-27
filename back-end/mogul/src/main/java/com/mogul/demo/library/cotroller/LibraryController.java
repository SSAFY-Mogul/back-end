package com.mogul.demo.library.cotroller;

import com.mogul.demo.library.dto.LibraryAddWebtoonRequest;
import com.mogul.demo.library.dto.LibraryCreateRequest;
import com.mogul.demo.library.service.LibraryService;
import com.mogul.demo.util.CustomResponse;
import com.mogul.demo.webtoon.service.WebtoonService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/library")
public class LibraryController {

    @Autowired
    LibraryService libraryService;

    @Autowired
    WebtoonService webtoonService;

    @GetMapping("/hot")
    public ResponseEntity<CustomResponse> libraryListHot(@RequestParam("pno") int pageNumber, @RequestParam("count") int pageSize){
        List data = libraryService.findLibrariesHot(pageNumber, pageSize);
        CustomResponse res = new CustomResponse<List>(200, data, "인기 서재 목록 조회 성공");
        return new ResponseEntity<CustomResponse>(res, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<CustomResponse> libraryList(){
        long userId = 1; // 로그인 구현 후 변경 요망!!!!!!!!
        List data = libraryService.findLibrariesByUserId(userId);
        CustomResponse res = new CustomResponse<List>(200, data, "사용자" + userId + "의 서재 목록 조회 성공");
        return new ResponseEntity<CustomResponse>(res, HttpStatus.OK);
    }

    @GetMapping("/{library-id}")
    public ResponseEntity<CustomResponse> libraryDetail(@PathVariable("library-id") long libraryId){
        Map<String, Object> data = new HashMap<>();
        data.put("libaray_detail", libraryService.findLibraryById(libraryId));
        data.put("included_webtoon", webtoonService.findWebtoonsByLibraryId(libraryId));
        CustomResponse res = new CustomResponse<Map>(200, data, "서재" + libraryId+"의 상세 정보 조회 성공");
        return new ResponseEntity<CustomResponse>(res, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<CustomResponse> libraryAdd(@RequestBody @Valid LibraryCreateRequest libraryCreateRequest, BindingResult bindingResult){
        CustomResponse res;
        if(bindingResult.hasErrors()){
            res = new CustomResponse(400, null, "잘못된 요청 형식 입니다.");
        }else{
            long userId = 1; // 로그인 구현 후 변경 요망!!!!!!!!
            libraryCreateRequest.setUserId(userId);
            Long data = libraryService.addLibrary(libraryCreateRequest);
            res = new CustomResponse<Long>(200, data, "서재 생성 성공");
        }
        return new ResponseEntity<CustomResponse>(res, HttpStatus.OK);
    }


    @DeleteMapping("/{library-id}")
    public ResponseEntity<CustomResponse> libraryRemove(@PathVariable("library-id") long id){
        CustomResponse res;
        boolean data = libraryService.removeLibrary(id);
        res = new CustomResponse<Boolean>(data?200:404, data, data?"서재 삭제 성공":"서재 삭제 실패");
        return new ResponseEntity<CustomResponse>(res, HttpStatus.OK);
    }

    @PostMapping("/{library-id}")
    public ResponseEntity<CustomResponse> libraryAddWebtoon(@PathVariable("library-id") long id, @RequestBody @Valid LibraryAddWebtoonRequest libraryAddWebtoonRequest, BindingResult bindingResult){
        CustomResponse res;
        if(bindingResult.hasErrors()){
            res = new CustomResponse(400, null, "잘못된 요청 형식 입니다.");
        }else{
            if(webtoonService.isExist(libraryAddWebtoonRequest.getWebtoonId())) {
                long userId = 1; // 로그인 구현 후 변경 요망!!!!!!!!!
                libraryAddWebtoonRequest.setId(id);
                boolean data = libraryService.addWebtoon(libraryAddWebtoonRequest);
                res = new CustomResponse<Boolean>(data ? 200 : 404, data, data ? "웹툰 추가 성공" : "웹툰 추가 실패");
            }else{
                res = new CustomResponse(400, null, "존재하지 않는 웹툰");
            }
        }
        return new ResponseEntity<CustomResponse>(res, HttpStatus.OK);
    }

    @GetMapping("/subscription")
    public ResponseEntity<CustomResponse> subscriptionList(@RequestParam("pno") int pageNumber, @RequestParam("count") int pageSize){
        long userId = 1; // 로그인 구현 후 변경 요망!!!!!!
        List data = libraryService.findSubscription(userId, pageNumber, pageSize);
        CustomResponse res = new CustomResponse<List>(200, data, "구독 중인 서재 조회 성공");
        return new ResponseEntity<CustomResponse>(res, HttpStatus.OK);
    }

}
