package com.mogul.demo.search.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.mogul.demo.search.dto.WebtoonSearchResponse;
import com.mogul.demo.search.dto.WebtoonTotalSearchResponse;
import com.mogul.demo.search.service.WebtoonSearchService;
import com.mogul.demo.util.CustomResponse;

@RestController
@RequestMapping("api/search")
public class SearchController {
	private final WebtoonSearchService webtoonSearchService;

	public SearchController(WebtoonSearchService webtoonSearchService) {
		this.webtoonSearchService = webtoonSearchService;
	}

	@GetMapping("/webtoon")
	public ResponseEntity<CustomResponse> search(@RequestParam("keyword") String keyword){
		WebtoonTotalSearchResponse list = webtoonSearchService.totalSearch(keyword);
		return ResponseEntity.ok(new CustomResponse(HttpStatus.OK.value(),list,"성공"));
	}

	@GetMapping("/webtoon/title")
	public ResponseEntity<CustomResponse> searchByWebtoonTitle(@RequestParam("keyword") String keyword){
		List<WebtoonSearchResponse> list = webtoonSearchService.findByTitle(keyword);
		if(list.isEmpty()){
			return ResponseEntity.ok(new CustomResponse(HttpStatus.NO_CONTENT.value(), "","데이터가 없습니다"));
		}
		return ResponseEntity.ok(new CustomResponse(HttpStatus.OK.value(),list,"성공"));
	}
}
