package com.mogul.demo.recommand.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "webtoon_embedding")
public class EmbeddingEntity {
    @Id
    @Column(name = "webtoon_id")
    private Long webtoonId;

    @Column(name = "embedding")
    private Long embedding;
}
