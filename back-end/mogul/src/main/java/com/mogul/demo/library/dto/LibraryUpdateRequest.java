package com.mogul.demo.library.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LibraryUpdateRequest {

    @NotBlank(message = "서재 이름은 비어 있을 수 없습니다.")
    @Size(max = 20, message = "서재 이름은 20자를 넘을 수 없습니다.")
    @Pattern(regexp = "^[가-힣a-zA-Z0-9?!@#$%^&()/]*$", message = "특수문자는 허용하지 않습니다.")
    private String name;

    private Long id;
}
