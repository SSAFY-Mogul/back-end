package com.mogul.demo.recommend.document;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

@Document(indexName = "webtoon-embeddings")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class WebtoonEmbedding {

    @Id
    @Field(name = "id", type = FieldType.Keyword)
    private String id;

    @Field(name = "webtoon_id", type = FieldType.Integer)
    private Long webtoonId;
}
