package com.mogul.demo.library.dto;

import lombok.*;

import java.util.Date;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class SubscriptionResponse {
    private long id;
    private long ownerId;
    private Date subscribeDate;
    private Date registeredDate;
    private Date deletedDate;
    private boolean isDeleted;
    private String name;
    private long subscriberNumber;
    private String thumbnail1;
    private String thumbnail2;
    private String thumbnail3;
    private String thumbnail4;
}
