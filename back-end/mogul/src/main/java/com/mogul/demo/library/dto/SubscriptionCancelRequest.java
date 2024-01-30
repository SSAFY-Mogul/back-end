package com.mogul.demo.library.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SubscriptionCancelRequest {

    @NotNull(message = "서재 식별자는 비어 있을 수 없습니다.")
    @Min(value = 1, message = "서재 식별자는 1이상이어야 합니다.")
    private long libraryId;

    private long userId;
}
