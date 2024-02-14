package com.mogul.demo.search.dto;

import java.util.List;

import lombok.Data;

@Data
public class WebtoonTotalSearchResponse {
	List<WebtoonSearchResponse> title;
	List<WebtoonSearchResponse> genre;
	List<WebtoonSearchResponse> summary;
}
