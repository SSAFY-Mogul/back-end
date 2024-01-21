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
    public WebtoonMainPageRes webtoonListMain(@RequestParam("pno") int page_number, @RequestParam("count") int page_size){
        return webtoonService.findWebtoonMain(page_number, page_size);
    }

    @GetMapping("/all")
    public WebtoonAllPageRes webtoonListAll(@RequestParam("pno") int page_number, @RequestParam("count") int page_size){
        return webtoonService.findWebtoonAll(page_number, page_size);
    }

    @GetMapping("/all/{genre}")
    public WebtoonGenrePageRes webtoonListGenre(@PathVariable("genre") String genre, @RequestParam("pno") int page_number, @RequestParam("count") int page_size){
        return webtoonService.findWebtoonByGenre(genre, page_number, page_size);
    }

    @GetMapping("/{webtoon-id}")
    public WebtoonDetailPageRes webtoonDetails(@PathVariable("webtoon-id") long webtoonId){
        return webtoonService.findWebtoonDetail(webtoonId);
    }
}
