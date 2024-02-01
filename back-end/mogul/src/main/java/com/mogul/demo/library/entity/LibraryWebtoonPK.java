package com.mogul.demo.library.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
public class LibraryWebtoonPK implements Serializable {
    private Long libraryId;
    private Long webtoonId;
}
