package com.mogul.demo.admin.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class WebtoonTagAddRequest {

    @NotBlank(message = "웹툰 태그는 비어 있을 수 없습니다.")
    @Size(min = 1, max = 20, message = "웹툰 태그의 길이는 1-20자 입니다.")
    @Pattern(regexp = "^[a-zA-Z0-9가-힣][a-zA-Z0-9가-힣_]*$")
    private String webtoonTag;

}
