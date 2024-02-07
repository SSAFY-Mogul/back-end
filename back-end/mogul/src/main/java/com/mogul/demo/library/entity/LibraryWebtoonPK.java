package com.mogul.demo.library.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LibraryWebtoonPK implements Serializable {
    private Long libraryId;
    private Long webtoonId;
}
