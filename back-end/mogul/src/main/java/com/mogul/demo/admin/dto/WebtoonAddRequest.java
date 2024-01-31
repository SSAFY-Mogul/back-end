package com.mogul.demo.admin.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class WebtoonAddRequest {

    @NotBlank(message = "웹툰 제목은 비어있을 수 없습니다.")
    @Size(min = 1, max = 75, message = "웹툰 제목의 길이는 1-75자 입니다.")
    @Pattern(regexp = "^[가-힣a-zA-Z0-9]*$", message = "웹툰 제목에 특수 문자는 허용하지 않습니다.")
    private String title;

    @NotBlank(message = "웹툰 작가는 비어있을 수 없습니다.")
    @Size(min = 1, max = 20, message = "웹툰 작가의 길이는 1-20자 입니다.")
    @Pattern(regexp = "^[가-힣a-zA-Z0-9]*$", message = "웹툰 작가에 특수문자는 허용하지 않습니다.")
    private String author;

    @NotBlank(message = "웹툰 장르는 비어있을 수 없습니다.")
    @Size(min = 1, max = 20, message = "웹툰 장르의 길이는 1-20자 입니다.")
    @Pattern(regexp = "^[가-힣a-zA-Z0-9]*$", message = "웹툰 장르에 특수문자는 허용하지 않습니다.")
    private String genre;

    @NotBlank(message = "웹툰 플랫폼은 비어있을 수 없습니다.")
    @Size(min = 1, max = 10, message = "웹툰 플랫폼의 길이는 1-10자 입니다.")
    @Pattern(regexp = "^[가-힣a-zA-Z0-9]*$", message = "웹툰 플랫폼에 특수문자는 허용하지 않습니다.")
    private String platform;

    @NotBlank(message = "웹툰 링크는 비어있을 수 없습니다.")
    @Size(min = 1, max = 255, message = "웹툰 링크의 길이는 1-255자 입니다.")
    @Pattern(regexp = "^(http|https):\\/\\/[a-zA-Z0-9\\-\\.]+\\.[a-zA-Z]{2,}(\\/[a-zA-Z0-9\\-\\._~:\\/\\?#\\[\\]@!\\$&'\\(\\)\\*\\+,;=]*)?$")
    private String link;

    @NotBlank(message = "웹툰 연재 시작일은 비어있을 수 없습니다.")
    @Size(min = 8, max = 8, message = "웹툰 연재 시작일은 8자 yyyymmdd형태 입니다.")
    @Pattern(regexp = "^(?:\\d{4}(?:0[1-9]|1[0-2])(?:0[1-9]|[12][0-9]|3[01]))$")
    private String startDate;

    @NotBlank(message = "웹툰 줄거리는 비어있을 수 없습니다.")
    @Size(min = 1, max = 3000, message = "웹툰 줄거리의 길이는 1-3000자 입니다.")
    @Pattern(regexp = "^[^<>]*$", message = "웹툰 줄거리에는 특수문자는 허용하지 않습니다.")
    private String summary;

    @Size(min = 1, max = 300, message = "웹툰 썸네일의 길이는 1-300자 입니다.")
    @Pattern(regexp = "^(http|https):\\/\\/[a-zA-Z0-9\\-\\.]+\\.[a-zA-Z]{2,}(\\/[a-zA-Z0-9\\-\\._~:\\/\\?#\\[\\]@!\\$&'\\(\\)\\*\\+,;=]*)?$")
    private String thumnail;

    private List<Long> tags;
}
