package com.mogul.demo.library.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Entity
@Table(name = "user_library")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@IdClass(LibraryUserPK.class)
public class LibraryUserEntity {
    @Id
    @Column(name = "library_id", nullable = false)
    private long libraryId;

    @Id
    @Column(name = "user_id",nullable = false)
    private long userId;

    @Column(name = "subscribe_registered_date", nullable = false)
    private Date registeredDate;
}
