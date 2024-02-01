package com.mogul.demo.library.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "library_webtoon")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode
@IdClass(LibraryWebtoonPK.class)
public class LibraryWebtoonEntity {
    @Id
    @Column(name = "library_id")
    private long libraryId;

    @Id
    @Column(name = "webtoon_id")
    private long webtoonId;
}
