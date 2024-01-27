package com.mogul.demo.library.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class LibraryAddWebtoonRequest {

    private long id;

    @NotNull(message = "웹툰 아이디를 입력해 주세요")
    @Min(value = 1L, message = "웹툰 아이디는 1이상 이어야 합니다.")
    private long webtoonId;

}
