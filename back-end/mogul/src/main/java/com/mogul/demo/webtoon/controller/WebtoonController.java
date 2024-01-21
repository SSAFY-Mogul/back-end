package com.mogul.demo.webtoon.controller;

import com.mogul.demo.webtoon.dto.WebtoonAllPageRes;
import com.mogul.demo.webtoon.dto.WebtoonDetailPageRes;
import com.mogul.demo.webtoon.dto.WebtoonGenrePageRes;
import com.mogul.demo.webtoon.dto.WebtoonMainPageRes;
import com.mogul.demo.webtoon.service.WebtoonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/webtoon")
public class WebtoonController {

    @Autowired
    WebtoonService webtoonService;

    @GetMapping
    public WebtoonMainPageRes webtoonListMain(@RequestParam("pno") int pageNumber, @RequestParam("count") int pageSize){
        return webtoonService.findWebtoonMain(pageNumber, pageSize);
    }

    @GetMapping("/all")
    public WebtoonAllPageRes webtoonListAll(@RequestParam("pno") int pageNumber, @RequestParam("count") int page_size){
        return webtoonService.findWebtoonAll(pageNumber, pageSize);
    }

    @GetMapping("/all/{genre}")
    public WebtoonGenrePageRes webtoonListGenre(@PathVariable("genre") String genre, @RequestParam("pno") int pageNumber, @RequestParam("count") int pageSize){
        return webtoonService.findWebtoonByGenre(genre, pageNumber, pageSize);
    }

    @GetMapping("/{webtoon-id}")
    public WebtoonDetailPageRes webtoonDetails(@PathVariable("webtoon-id") long webtoonId, @RequestParam("pno") int pageNumber, @RequestParam("count") int pageSize){
        return webtoonService.findWebtoonDetail(webtoonId, pageNumber, pageSize);
    }
}
