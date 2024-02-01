package com.mogul.demo.library.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode
public class SubcriptionRequest {

    @NotNull(message = "서재 아이디는 비어 있을 수 없습니다.")
    @Min(value = 1L, message = "서재 아이디는 1이상 이어야 합니다.")
    private Long libraryId;

    private Long userId;

    private Date registeredDate;
}
