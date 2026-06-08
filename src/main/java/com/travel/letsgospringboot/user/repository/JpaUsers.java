package com.travel.letsgospringboot.user.repository;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.sql.Timestamp;

@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class JpaUsers {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String userID;
    private String email;
    private String name;
    private String password;
    private String role;

    @CreationTimestamp
    @Column(updatable = false)
    private Timestamp created;
}
