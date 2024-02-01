package com.mogul.demo.webtoon.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "webtoon_webtoon_tag")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@IdClass(WebtoonWebtoonTagPK.class)
public class WebtoonWebtoonTagEntity {
    @Id
    @Column(name = "webtoon_id", nullable = false)
    private Long webtoonId;

    @Id
    @Column(name = "webtoon_tag_id",nullable = false)
    private Long tagId;
}
