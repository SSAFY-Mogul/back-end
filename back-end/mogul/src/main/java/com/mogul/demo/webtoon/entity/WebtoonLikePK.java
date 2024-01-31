package com.mogul.demo.webtoon.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class WebtoonLikePK implements Serializable {
    private Long webtoonId;
    private Long userId;
}
