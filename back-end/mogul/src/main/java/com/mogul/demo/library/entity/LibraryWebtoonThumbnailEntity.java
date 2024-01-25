package com.mogul.demo.library.entity;

import com.mogul.demo.library.pk.LibraryWebtoonThumbnailPK;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Entity
@Table(name = "library_webtoon_thumbnail")
@Getter
@Setter
@NoArgsConstructor
@IdClass(LibraryWebtoonThumbnailPK.class)
public class LibraryWebtoonThumbnailEntity {
    @Id
    @Column(name = "library_id", nullable = false)
    private long libraryId;

    @Id
    @Column(name = "webtoon_id",nullable = false)
    private long webtoonId;

    @Column(name = "library_is_deleted")
    private boolean isDeleted;

    @Column(name = "library_name", nullable = false)
    private String name;

    @Column(name = "library_subscriber_number", nullable = false)
    private long subscriberNumber;

    @Column(name = "library_deleted_date")
    private Date deletedDate;

    @Column(name = "library_registered_date", nullable = false)
    private Date registeredDate;

    @Column(name = "thumbnail_1")
    private String thumbnail1;

    @Column(name = "thumbnail_2")
    private String thumbnail2;

    @Column(name = "thumbnail_3")
    private String thumbnail3;

    @Column(name = "thumbnail_4")
    private String thumbnail4;

    @Column(name = "user_id")
    private long userId;

}
