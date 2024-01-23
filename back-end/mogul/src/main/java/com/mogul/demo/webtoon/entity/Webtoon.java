package com.mogul.demo.webtoon.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Entity
@Table(name = "webtoon")
@Getter
@Setter
@NoArgsConstructor
public class Webtoon {

    @Id
    @Column(name="webtoon_id", nullable = false)
    private long id;

    @Column(name = "webtoon_title", nullable = false)
    private String title;

    @Column(name = "webtoon_author", nullable = false)
    private String author;

    @Column(name = "webtoon_genre", nullable = false)
    private String genre;

    @Column(name = "webtoon_publisher", nullable = false)
    private String publisher;

    @Column(name = "webtoon_platform", nullable = false)
    private String platform;

    @Column(name = "webtoon_link", nullable = false)
    private String link;

    @Column(name = "webtoon_start_date", nullable = false)
    private Date startDate;

    @Column(name = "webtoon_summary", nullable = false)
    private String summary;

    @Column(name = "webtoon_grade", nullable = false)
    private float grade;

    @Column(name = "webtoon_drawing_grade", nullable = false)
    private float drawingGrade;

    @Column(name = "webtoon_stroy_grade", nullable = false)
    private float storyGrade;

    @Column(name = "webtoon_directing_grade", nullable = false)
    private float directingGrade;

    @Column(name = "webtoon_registed_date", nullable = false)
    private Date registerdDate;

    @Column(name = "webtoon_deleted_date")
    private Date deletedDate;

    @Column(name = "webtoon_is_deleted")
    private boolean isDeleted;

    @Column(name = "webtoon_thumbnail", nullable = false)
    private String thumbnail;
}
