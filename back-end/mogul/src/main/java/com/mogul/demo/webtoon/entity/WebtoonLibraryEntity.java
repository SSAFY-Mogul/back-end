package com.mogul.demo.webtoon.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Immutable;

import java.util.Date;
import java.util.List;

@Immutable
@Entity
@Table(name = "webtoon_library")
@Getter
@Setter
@NoArgsConstructor
@IdClass(WebtoonLibraryPK.class)
public class WebtoonLibraryEntity {

    @Id
    @Column(name = "library_id", nullable = false)
    private Long libraryId;

    @Id
    @Column(name = "webtoon_id", nullable = false)
    private Long webtoonId;

    @Column(name = "webtoon_title", nullable = false)
    private String title;

    @Column(name = "webtoon_author", nullable = false)
    private String author;

    @Column(name = "webtoon_genre", nullable = false)
    private String genre;

    @Column(name = "webtoon_platform", nullable = false)
    private String platform;

    @Column(name = "webtoon_link", nullable = false)
    private String link;

    @Column(name = "webtoon_start_date", nullable = false)
    private String startDate;

    @Column(name = "webtoon_summary", nullable = false)
    private String summary;

    @Column(name = "webtoon_grade", nullable = false)
    private Float grade;

    @Column(name = "webtoon_drawing_grade", nullable = false)
    private Float drawingGrade;

    @Column(name = "webtoon_story_grade", nullable = false)
    private Float storyGrade;

    @Column(name = "webtoon_directing_grade", nullable = false)
    private Float directingGrade;

    @Column(name = "webtoon_registered_date", nullable = false)
    private Date registerdDate;

    @Column(name = "webtoon_deleted_date")
    private Date deletedDate;

    @Column(name = "webtoon_is_deleted")
    private Boolean isDeleted;

    @Column(name = "webtoon_thumbnail", nullable = false)
    private String thumbnail;

}
