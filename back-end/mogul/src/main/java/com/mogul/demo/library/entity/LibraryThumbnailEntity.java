package com.mogul.demo.library.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Entity
@Table(name = "library_thumbnail")
@Getter
@Setter
@NoArgsConstructor
public class LibraryThumbnailEntity {

    @Id
    @Column(name = "library_id", nullable = false)
    private Long id;

    @Column(name = "library_is_deleted")
    private Boolean isDeleted;

    @Column(name = "library_name", nullable = false)
    private String name;

    @Column(name = "library_subscriber_number", nullable = false)
    private Long subscriberNumber;

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

    @Column(name = "user_id", nullable = false)
    private Long userId;

    @Column(name = "user_nickname", nullable = false)
    private String nickname;
}
