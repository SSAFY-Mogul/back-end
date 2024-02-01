package com.mogul.demo.library.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class LibraryResponse {
    private Long id;
    private String name;
    private Long subscriberNumber;
    private Date registeredDate;
    private String thumbnail1;
    private String thumbnail2;
    private String thumbnail3;
    private String thumbnail4;
}
