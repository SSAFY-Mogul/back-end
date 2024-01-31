package com.mogul.demo.webtoon.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
public class WebtoonLibraryPK implements Serializable {
    private Long libraryId;
    private Long webtoonId;
}
