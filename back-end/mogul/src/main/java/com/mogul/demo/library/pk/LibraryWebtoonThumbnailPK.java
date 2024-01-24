package com.mogul.demo.library.pk;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Data
@Getter
@Setter
@NoArgsConstructor
public class LibraryWebtoonThumbnailPK implements Serializable {
    private long libraryId;
    private long webtoonId;
}
