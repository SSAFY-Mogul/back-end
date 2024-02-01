package com.mogul.demo.review.dto;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReviewAddRequest {

    private Long webtoonId;

    private Long userId;

    @NotBlank(message = "리뷰 제목은 비어 있을 수 없습니다.")
    @Size(max = 50, min = 1, message = "리뷰 제목은 50자 이하여야 합니다.")
    @Pattern(regexp = "^[가-힣a-zA-Z0-9?/!@#$%^&*_]*$", message = "특수문자는 허용하지 않습니다.")
    private String title;

    @NotBlank(message = "리뷰 내용은 비어 있을 수 없습니다.")
    @Size(max = 300, min = 1, message = "리뷰 내용은 1000자 이하여야 합니다.")
    @Pattern(regexp = "^[가-힣a-zA-Z0-9?/!@#$%^&*_]*$", message = "특수문자는 허용하지 않습니다.")
    private String content;

    @NotNull(message = "작화 평점은 비어있을 수 없습니다.")
    @Max(value = 5, message = "평점은 5점보다 클 수 없습니다.")
    @Min(value = 0, message = "평점은 0점보다 작을 수 없습니다.")
    private Integer drawingScore;

    @NotNull(message = "스토리 평점은 비어있을 수 없습니다.")
    @Max(value = 5, message = "평점은 5점보다 클 수 없습니다.")
    @Min(value = 0, message = "평점은 0점보다 작을 수 없습니다.")
    private Integer storyScore;

    @NotNull(message = "연출 평점은 비어있을 수 없습니다.")
    @Max(value = 5, message = "평점은 5점보다 클 수 없습니다.")
    @Min(value = 0, message = "평점은 0점보다 작을 수 없습니다.")
    private Integer directingScore;

    private Date registeredDate;

}
