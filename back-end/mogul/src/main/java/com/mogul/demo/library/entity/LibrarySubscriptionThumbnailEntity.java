package com.mogul.demo.library.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Entity
@Table(name = "library_subscription_thumbnail")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@IdClass(LibrarySubscriptionThumbnailPK.class)
public class LibrarySubscriptionThumbnailEntity {
    @Id
    @Column(name = "library_id", nullable = false)
    private long id;

    @Id
    @Column(name = "user_id", nullable = false)
    private long userId;

    @Column(name = "library_owner_id", nullable = false)
    private long ownerId;

    @Column(name = "library_registered_date", nullable = false)
    private Date registeredDate;

    @Column(name = "library_deleted_date")
    private Date deletedDate;

    @Column(name = "library_is_deleted")
    private boolean isDeleted;

    @Column(name = "library_name")
    private String name;

    @Column(name = "library_subscriber_number")
    private long subscirberNumber;

    @Column(name = "thumbnail_1")
    private String thumbnail1;

    @Column(name = "thumbnail_2")
    private String thumbnail2;

    @Column(name = "thumbnail_3")
    private String thumbnail3;

    @Column(name = "thumbnail_4")
    private String thumbnail4;

}
