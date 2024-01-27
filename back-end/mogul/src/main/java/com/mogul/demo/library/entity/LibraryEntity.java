package com.mogul.demo.library.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;

import java.util.Date;

@Entity
@Table(name = "library")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LibraryEntity {

    @Id
    @Column(name = "library_id", nullable = true)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "library_name", nullable = false)
    private String name;

    @Column(name = "library_registered_date", nullable = false)
    @CreationTimestamp
    private Date registeredDate;

    @Column(name = "library_deleted_date")
    private Date deletedDate;

    @Column(name = "library_is_deleted")
    private boolean isDeleted;

    @Column(name = "library_subscriber_number", nullable = false)
    private long subscriberNumber;

    @Column(name = "user_id", nullable = false)
    private long userId;
}
