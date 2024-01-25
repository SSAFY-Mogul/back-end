package com.mogul.demo.library.cotroller;

import com.mogul.demo.library.service.LibraryService;
import com.mogul.demo.util.CustomResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/library")
public class LibraryController {

    @Autowired
    LibraryService libraryService;

    @GetMapping("/hot")
    public ResponseEntity<CustomResponse> libraryListHot(@RequestParam("pno") int pageNumber, @RequestParam("count") int pageSize){
        List data = libraryService.findLibrariesHot(pageNumber, pageSize);
        CustomResponse res = new CustomResponse<List>(200, data, "인기 서재 목록 읽기 성공");
        return new ResponseEntity<CustomResponse>(res, HttpStatus.ACCEPTED);
    }
}
