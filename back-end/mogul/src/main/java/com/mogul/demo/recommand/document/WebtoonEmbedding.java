package com.mogul.demo.recommand.document;

import jakarta.persistence.Id;
import lombok.*;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.util.List;

@Document(indexName = "webtoon-embeddings")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class WebtoonEmbedding {
    @Id
    @Field(name = "webtoon_id", type = FieldType.Integer)
    Long webtoonId;

    @Field(name = "webtoon_embedding", type = FieldType.Dense_Vector)
    List<Double> embedding;
}
