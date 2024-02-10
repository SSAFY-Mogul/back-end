package com.mogul.demo.search.dto;

import lombok.Data;

@Data
public class WebtoonSearchResponse {
	private long webtoon_id;
	private String title;
	private String summary;
	private String author;
	private String platform;
	private String thumbnail;
}
