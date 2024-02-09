package com.mogul.demo.search.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.mogul.demo.search.dto.WebtoonSearchResponse;
import com.mogul.demo.search.service.SearchService;
import com.mogul.demo.util.CustomResponse;

@RestController
@RequestMapping("api/search")
public class SearchController {
	private final SearchService searchService;

	public SearchController(SearchService searchService) {
		this.searchService = searchService;
	}

	@GetMapping("")
	public ResponseEntity<CustomResponse> search(@RequestParam("keyword") String keyword){
		List<WebtoonSearchResponse> list = searchService.search(keyword);
		return ResponseEntity.ok(new CustomResponse(HttpStatus.OK.value(),list,"성공"));
	}
}
