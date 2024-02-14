package com.mogul.demo.recommend.dto;

import com.mogul.demo.webtoon.dto.WebtoonDetailResponse;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class WebtoonRecommendResponse extends WebtoonInfo{
	private WebtoonDetailResponse webtoon;
}
