package com.mogul.demo.webtoon.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
public class WebtoonDetailResponse {
    private Long id;
    private String title;
    private String author;
    private String genre;
    private String platform;
    private String link;
    private LocalDate startDate;
    private String summary;
    private String grade;
    private String drawingGrade;
    private String StoryGrade;
    private String directingGrade;
    private Date registeredDate;
    private String thumbnail;
}
