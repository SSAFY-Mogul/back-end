package com.mogul.demo.library.dto;

import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class LibraryAddWebtoonRequest {

    private long id;

    @NotNull(message = "웹툰 아이디를 입력해 주세요")
    private long webtoonId;

}
