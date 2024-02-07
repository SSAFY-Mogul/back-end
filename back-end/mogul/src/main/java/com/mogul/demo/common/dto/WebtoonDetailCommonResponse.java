package com.mogul.demo.common.dto;

import com.mogul.demo.library.dto.LibraryResponse;
import com.mogul.demo.review.dto.ReviewResponse;
import com.mogul.demo.webtoon.dto.WebtoonDetailResponse;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class WebtoonDetailCommonResponse {
    private WebtoonDetailResponse webtoonDetail;
    private List<ReviewResponse> reviews;
    private List<LibraryResponse> libraries;
}
