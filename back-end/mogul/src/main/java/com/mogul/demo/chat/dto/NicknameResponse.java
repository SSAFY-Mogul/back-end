package com.mogul.demo.chat.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@EqualsAndHashCode
public class NicknameResponse {

    @JsonProperty
    private String nickname;

}
