package com.mogul.demo.review.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
public class ReviewResponse {
    private long id;
    private long userId;
    private String title;
    private String content;
    private Date registeredDate;
    private int drawingScore;
    private int directingScore;
    private int storyScore;
    
}
