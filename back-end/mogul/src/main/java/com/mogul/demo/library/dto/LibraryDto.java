package com.mogul.demo.library.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
public class LibraryDto {
    private long id;
    private String name;
    private long subscriberNumber;
    private Date registeredDate;
    private String thumbnail1;
    private String thumbnail2;
    private String thumbnail3;
    private String thumbnail4;
}
