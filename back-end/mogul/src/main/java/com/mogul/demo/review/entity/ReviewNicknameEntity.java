package com.mogul.demo.review.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
@Table(name = "review_nickname")
public class ReviewNicknameEntity {
    @Id
    @Column(name = "review_id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_id", nullable = false)
    private Long userId;

    @Column(name = "user_nickname", nullable = false)
    private String nickname;

    @Column(name = "review_title", nullable = false)
    private String title;

    @Column(name = "review_content", nullable = false)
    private String content;

    @Column(name = "review_registered_date", nullable = false)
    @CreationTimestamp
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
    private Boolean isDeleted = false;

    @Column(name = "webtoon_id")
    private Long webtoonId;
}
