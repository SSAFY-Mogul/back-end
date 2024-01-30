package com.mogul.demo.webtoon.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class WebtoonWebtoonTagTagPK implements Serializable {
    private long webtoonId;
    private long tagId;
}
