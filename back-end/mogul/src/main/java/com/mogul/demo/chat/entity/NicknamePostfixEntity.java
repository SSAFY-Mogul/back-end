package com.mogul.demo.chat.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.mongodb.repository.CountQuery;

@Entity
@Table(name = "nickname_postfix")
@Getter
@Setter
@NoArgsConstructor
public class NicknamePostfixEntity {

    @Id
    @Column(name = "nickname_postfix_id", nullable = false)
    private Long id;

    @Column(name = "nickname_postfix_value", nullable = false)
    private String postfix;
}
