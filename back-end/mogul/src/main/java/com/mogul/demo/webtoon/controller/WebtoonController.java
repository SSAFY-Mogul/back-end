package com.mogul.demo.webtoon.controller;

import com.mogul.demo.webtoon.response.WebtoonMainPageResponse;
import com.mogul.demo.webtoon.service.WebtoonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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
    public WebtoonMainPageResponse webtoonListMain(@RequestParam("pno") int pageNumber, @RequestParam("count") int pageSize){
        Map<String, List> data = new HashMap<>();
        data.put("webtoon-top-grade", webtoonService.findWebtoonOrderByGrade(pageNumber, pageSize));
        data.put("webtoon-top-library", webtoonService.findWebtoonOrderByLibraryCount(pageNumber, pageSize));
        WebtoonMainPageResponse webtoonMainPageResponse = new WebtoonMainPageResponse(data, HttpStatus.ACCEPTED);
        return webtoonMainPageResponse;
    }

//    @GetMapping("/all")
//    public WebtoonAllPageRes webtoonListAll(@RequestParam("pno") int pageNumber, @RequestParam("count") int pageSize){
//        return webtoonService.findWebtoonAll(pageNumber, pageSize);
//    }
//
//    @GetMapping("/all/{genre}")
//    public WebtoonGenrePageRes webtoonListGenre(@PathVariable("genre") String genre, @RequestParam("pno") int pageNumber, @RequestParam("count") int pageSize){
//        return webtoonService.findWebtoonByGenre(genre, pageNumber, pageSize);
//    }
//
//    @GetMapping("/{webtoon-id}")
//    public WebtoonDetailPageRes webtoonDetails(@PathVariable("webtoon-id") long webtoonId, @RequestParam("pno") int pageNumber, @RequestParam("count") int pageSize){
//        return webtoonService.findWebtoonDetail(webtoonId, pageNumber, pageSize);
//    }


}
