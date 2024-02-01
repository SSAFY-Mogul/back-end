package com.mogul.demo.webtoon.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "webtoon_tag")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class WebtoonTagEntity {
    @Id
    @Column(name = "webtoon_tag_id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(name = "webtoon_tag", nullable = false)
    String tag;
}
