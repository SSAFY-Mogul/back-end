package com.mogul.demo.webtoon.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "webtoon_webtoon_tag_tag")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode
@IdClass(WebtoonWebtoonTagTagPK.class)
public class WebtoonWebtoonTagTagEntity {

    @Id
    @Column(name = "webtoon_id", nullable = false)
    private long webtoonId;

    @Id
    @Column(name = "webtoon_tag_id", nullable = false)
    private long tagId;

    @Column(name = "webtoon_tag", nullable = false)
    private String tag;

}
