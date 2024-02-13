package com.mogul.demo.common.dto;

import com.mogul.demo.webtoon.dto.WebtoonDetailResponse;
import com.mogul.demo.webtoon.dto.WebtoonResponse;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RecommandResponse {
    private Long webtoonId;
    private double score;
    WebtoonDetailResponse webtoon;
}
