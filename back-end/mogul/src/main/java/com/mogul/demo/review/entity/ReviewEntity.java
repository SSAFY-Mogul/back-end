package com.mogul.demo.review.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.springframework.boot.context.properties.bind.DefaultValue;

import java.util.Date;

@Entity
@Table(name = "review")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReviewEntity {

    @Id
    @Column(name = "review_id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_id", nullable = false)
    private Long userId;

    @Column(name = "review_title", nullable = false)
    private String title;

    @Column(name = "review_content", nullable = false)
    private String content;

    @Column(name = "review_registered_date", nullable = false)
    private Date registeredDate;

    @Column(name = "review_drawing_score", nullable = false)
    private Integer drawingScore;

    @Column(name = "review_directing_score", nullable = false)
    private Integer directingScore;

    @Column(name = "review_story_score", nullable = false)
    private Integer storyScore;

    @Column(name = "review_deleted_date")
    private Date deletedDate;

    @Column(name = "review_is_deleted", nullable = false)
    @Builder.Default
    private Boolean isDeleted = false;

    @Column(name = "webtoon_id")
    private Long webtoonId;

}
