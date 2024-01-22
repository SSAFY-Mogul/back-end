package com.mogul.demo.review.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Entity
@Table(name = "@review")
@Getter
@Setter
@NoArgsConstructor
public class ReviewEntity {

    @Id
    @Column(name = "review_id", nullable = false)
    private long id;

    @Column(name = "review_title", nullable = false)
    private String title;

    @Column(name = "review_content", nullable = false)
    private String content;

    @Column(name = "review_registered_date", nullable = false)
    private Date registeredDate;

    @Column(name = "review_drawing_score", nullable = false)
    private int drawingScore;

    @Column(name = "review_directing_score", nullable = false)
    private int directingScore;

    @Column(name = "review_story_score", nullable = false)
    private int storyScore;

    @Column(name = "review_deleted_date")
    private Date deletedDate;

    @Column(name = "review_is_deleted")
    private boolean isDeleted;

}
