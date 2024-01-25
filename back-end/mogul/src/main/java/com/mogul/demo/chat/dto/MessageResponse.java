package com.mogul.demo.chat.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode
public class MessageResponse {

    @JsonProperty("writer")
    private String nickname;

    @JsonProperty("msg")
    private String message;

}
