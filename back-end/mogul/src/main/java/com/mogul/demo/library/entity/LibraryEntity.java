package com.mogul.demo.library.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.util.Date;

@Entity
@Table(name = "library")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LibraryEntity {

    @Id
    @Column(name = "library_id", nullable = true)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "library_name", nullable = false)
    private String name;

    @Column(name = "library_registered_date", nullable = false)
    @CreationTimestamp
    private Date registeredDate;

    @Column(name = "library_deleted_date")
    private Date deletedDate;

    @Column(name = "library_is_deleted", nullable = false)
    @Builder.Default
    private Boolean isDeleted = false;

    @Column(name = "library_subscriber_number", nullable = false)
    @Builder.Default
    private Long subscriberNumber = 0L;

    @Column(name = "user_id", nullable = false)
    private Long userId;
}
