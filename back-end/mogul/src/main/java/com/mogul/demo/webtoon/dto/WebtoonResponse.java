package com.mogul.demo.webtoon.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class WebtoonResponse {
    private long id;
    private String title;
    private String author;
    private String genre;
    private String platform;
    private String thumbnail;
}
