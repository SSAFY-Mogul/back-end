package com.mogul.demo.library.entity;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Data
@NoArgsConstructor
public class LibraryWebtoonThumbnailPK implements Serializable {
    private long libraryId;
    private long webtoonId;
}
