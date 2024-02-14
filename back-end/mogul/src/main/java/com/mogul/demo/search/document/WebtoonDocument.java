package com.mogul.demo.search.document;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Document(indexName = "search-webtoon")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class WebtoonDocument {

	@Id
	@Field(name = "id",type = FieldType.Keyword)
	private String id;

	@Field(name = "webtoon_id",type = FieldType.Long)
	private Long webtoon_id;

	@Field(name = "webtoon_title", type = FieldType.Text)
	private String title;

	@Field(name = "webtoon_author", type = FieldType.Keyword)
	private String author;

	@Field(name = "webtoon_genre", type = FieldType.Keyword)
	private String genre;

	@Field(name = "webtoon_platform", type = FieldType.Keyword)
	private String platform;

	@Field(name = "webtoon_start_date", type = FieldType.Long)
	private String startDate;

	@Field(name = "webtoon_summary", type = FieldType.Text)
	private String summary;

	@Field(name = "webtoon_thumbnail", type = FieldType.Keyword)
	private String thumbnail;
}
