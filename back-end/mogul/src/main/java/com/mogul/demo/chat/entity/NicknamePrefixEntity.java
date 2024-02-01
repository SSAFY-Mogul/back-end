package com.mogul.demo.chat.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "nickname_prefix")
@Getter
@Setter
@NoArgsConstructor
public class NicknamePrefixEntity {

    @Id
    @Column(name = "nickname_prefix_id", nullable = false)
    private Long id;

    @Column(name = "nickname_prefix_value", nullable = false)
    private String prefix;

}
