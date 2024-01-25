package com.mogul.demo.library.cotroller;

import com.mogul.demo.library.service.LibraryService;
import com.mogul.demo.util.CustomResponse;
import com.mogul.demo.webtoon.service.WebtoonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
        return new ResponseEntity<CustomResponse>(res, HttpStatus.ACCEPTED);
    }

    @GetMapping
    public ResponseEntity<CustomResponse> libraryList(){
        long userId = 1; // 로그인 구현 후 변경 요망!!!!!!!!
        List data = libraryService.findLibrariesByUserId(userId);
        CustomResponse res = new CustomResponse<List>(200, data, "사용자" + userId + "의 서재 목록 조회 성공");
        return new ResponseEntity<CustomResponse>(res, HttpStatus.ACCEPTED);
    }

    @GetMapping("/{library-id}")
    public ResponseEntity<CustomResponse> LibraryDetail(@PathVariable("library-id}") long libraryId){
        Map<String, Object> data = new HashMap<>();
        data.put("libaray_detail", libraryService.findLibraryById(libraryId));
        data.put("included_webtoon", webtoonService.findWebtoonsByLibraryId(libraryId));
        CustomResponse res = new CustomResponse<Map>(200, data, "서재" + libraryId+"의 상세 정보 조회 성공");
        return new ResponseEntity<CustomResponse>(res, HttpStatus.ACCEPTED);
    }

}
