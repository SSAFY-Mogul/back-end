package com.mogul.demo.review.dto;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReviewUpdateRequest {

    private Long id;

    @NotBlank(message = "리뷰 제목은 비어 있을 수 없습니다.")
    @Size(max = 50, min = 1, message = "리뷰 제목은 50자를 넘을 수 없습니다.")
    private String title;

    @NotBlank(message = "리뷰 내용은 비어있을 수 없습니다.")
    @Size(max = 1000, min = 1, message = "리뷰 내용은 1000자를 넘을 수 없습니다.")
    private String content;

    @NotNull(message = "작화 평점은 비어있을 수 없습니다.")
    @Max(value = 5, message = "작화 평점은 5점보다 클 수 없습니다.")
    @Min(value = 0, message = "작화 평점은 1점보다 작을 수 없습니다.")
    private Integer drawingScore;

    @NotNull(message = "스토리 평점은 비어있을 수 없습니다.")
    @Max(value = 5, message = "스토리 평점은 5점보다 클 수 없습니다.")
    @Min(value = 0, message = "스토리 평점은 1점보다 작을 수 없습니다.")
    private Integer storyScore;

    @NotNull(message = "연출 평점은 비어있을 수 없습니다.")
    @Max(value = 5, message = "연출 평점은 5점보다 클 수 없습니다.")
    @Min(value = 0, message = "연출 평점은 1점보다 작을 수 없습니다.")
    private Integer directingScore;

}
