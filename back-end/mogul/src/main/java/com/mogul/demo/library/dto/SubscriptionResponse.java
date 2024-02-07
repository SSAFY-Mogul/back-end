package com.mogul.demo.library.dto;

import lombok.*;

import java.util.Date;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class SubscriptionResponse {
    private Long id;
    private Long ownerId;
    private String ownerNickname;
    private Date subscribeDate;
    private Date registeredDate;
    private Date deletedDate;
    private Boolean isDeleted;
    private String name;
    private Long subscriberNumber;
    private String thumbnail1;
    private String thumbnail2;
    private String thumbnail3;
    private String thumbnail4;
}
