package com.mogul.demo.library.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotBlank;
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
    private String name;

    private long id;
}
