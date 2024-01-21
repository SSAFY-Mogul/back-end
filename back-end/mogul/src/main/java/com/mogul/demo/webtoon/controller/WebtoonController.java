package com.mogul.demo.webtoon.controller;

import com.mogul.demo.webtoon.dto.WebtoonMainPageRes;
import com.mogul.demo.webtoon.service.WebtoonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/webtoon")
public class WebtoonController {

    @Autowired
    WebtoonService webtoonService;

    @GetMapping("/")
    public WebtoonMainPageRes webtoonListMain(@RequestParam("pno") int page_number, @RequestParam("count") int page_size){

    }

}
