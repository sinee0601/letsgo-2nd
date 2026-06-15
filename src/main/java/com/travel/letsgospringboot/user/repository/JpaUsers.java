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

    @Column(unique = true)
    private String userID;

    @Column(unique = true)
    private String email;

    private String name;
    private String password;
    private String role;

    @CreationTimestamp
    @Column(updatable = false)
    private Timestamp created;
    
    @Builder.Default
    @Column(nullable = false, columnDefinition = "integer default 0")
    private int warningCount = 0;

    @Builder.Default
    @Column(nullable = false, columnDefinition = "boolean default true")
    private boolean enabled = true;
}
