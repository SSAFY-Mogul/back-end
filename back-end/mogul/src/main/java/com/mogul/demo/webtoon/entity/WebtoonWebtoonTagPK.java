package com.mogul.demo.webtoon.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class WebtoonWebtoonTagPK implements Serializable {
    private Long webtooId;
    private Long tagId;
}
