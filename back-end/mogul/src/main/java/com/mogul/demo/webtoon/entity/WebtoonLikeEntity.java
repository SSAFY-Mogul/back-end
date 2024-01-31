package com.mogul.demo.webtoon.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "webtoon_like")
@Data
@NoArgsConstructor
@AllArgsConstructor
@IdClass(WebtoonLikePK.class)
public class WebtoonLikeEntity {

    @Id
    @Column(name = "webtoon_id")
    private long webtoonId;

    @Id
    @Column(name = "user_id")
    private long userId;

}
