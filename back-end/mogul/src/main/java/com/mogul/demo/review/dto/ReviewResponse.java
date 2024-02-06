package com.mogul.demo.review.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
public class ReviewResponse {
    private Long id;
    private Long userId;
    private String nickname;
    private String title;
    private String content;
    private Date registeredDate;
    private Integer drawingScore;
    private Integer directingScore;
    private Integer storyScore;
    
}
