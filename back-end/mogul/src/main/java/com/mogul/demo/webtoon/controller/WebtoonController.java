package com.mogul.demo.webtoon.controller;

import com.mogul.demo.webtoon.service.WebtoonService;
import org.apache.coyote.Response;
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

    @GetMapping
    public ResponseEntity webtoonListMain(@RequestParam("pno") int pageNumber, @RequestParam("count") int pageSize){
        Map<String, List> data = new HashMap<>();
        data.put("webtoon-top-grade", webtoonService.findWebtoonOrderByGrade(pageNumber, pageSize));
        data.put("webtoon-top-library", webtoonService.findWebtoonOrderByLibraryCount(pageNumber, pageSize));
        ResponseEntity<Map> res = new ResponseEntity<>(data, HttpStatus.ACCEPTED);
        return res;
    }

    @GetMapping("/all")
    public ResponseEntity webtoonListAll(@RequestParam("pno") int pageNumber, @RequestParam("count") int pageSize){
        List data = webtoonService.findWebtoonAll(pageNumber, pageSize);
        ResponseEntity<List> res = new ResponseEntity<>(data, HttpStatus.ACCEPTED);
        return res;
    }

    @GetMapping("/all/{genre}")
    public ResponseEntity webtoonListGenre(@PathVariable("genre") String genre, @RequestParam("pno") int pageNumber, @RequestParam("count") int pageSize){
        List data = webtoonService.findWebtoonAllByGenre(genre, pageNumber, pageSize);
        ResponseEntity<List> res = new ResponseEntity<>(data, HttpStatus.ACCEPTED);
        return res;
    }
//
//    @GetMapping("/{webtoon-id}")
//    public WebtoonDetailPageRes webtoonDetails(@PathVariable("webtoon-id") long webtoonId, @RequestParam("pno") int pageNumber, @RequestParam("count") int pageSize){
//        return webtoonService.findWebtoonDetail(webtoonId, pageNumber, pageSize);
//    }


}
