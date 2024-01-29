package com.mogul.demo.webtoon.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class WebtoonTagResponse {

    private long tagId;
    private String tag;

}
