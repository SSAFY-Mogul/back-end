package com.mogul.demo.webtoon.controller;

import com.mogul.demo.webtoon.dto.WebtoonDto;
import com.mogul.demo.webtoon.response.WebtoonAllPageRes;
import com.mogul.demo.webtoon.response.WebtoonDetailPageRes;
import com.mogul.demo.webtoon.response.WebtoonGenrePageRes;
import com.mogul.demo.webtoon.response.WebtoonMainPageRes;
import com.mogul.demo.webtoon.service.WebtoonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/webtoon")
public class WebtoonController {

    @Autowired
    WebtoonService webtoonService;

    @GetMapping
    public List<WebtoonDto> webtoonListMain(@RequestParam("pno") int pageNumber, @RequestParam("count") int pageSize){
        return webtoonService.findWebtoonOrderByGrade(pageNumber, pageSize);
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
