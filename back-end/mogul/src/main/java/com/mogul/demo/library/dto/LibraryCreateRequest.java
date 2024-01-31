package com.mogul.demo.library.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class LibraryCreateRequest {

    @NotBlank(message = "서재 이름은 비어있을 수 없습니다.")
    @Size(max = 20, message = "서재이름은 20글자를 넘을 수 없습니다.")
    @Pattern(regexp = "^[가-힣a-zA-Z0-9?!@#$%^&()/]*$", message = "특수문자는 허용하지 않습니다.")
    private String name;

    private Long userId;

}
