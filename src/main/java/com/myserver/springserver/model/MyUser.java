package com.myserver.springserver.model;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Entity
@Builder
@Table(name = "Users")
public class MyUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;

    @Column(unique = true)
    public String username;

    @Column(unique = true)
    public String email;

    public String password;

    public String gender;

    public LocalDate birthday;

    @Enumerated(EnumType.STRING)
    public Role role;

    @CreationTimestamp
    public LocalDateTime created;

    public LocalDateTime updated;

    public MyUser() {}

    public MyUser(String username, String email, String password, String gender, LocalDate birthday, Role role) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.gender = gender;
        this.birthday = birthday;
        this.role = role;
    }

    public MyUser(Long id, String username, String email, String password, String gender, LocalDate birthday, Role role) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.password = password;
        this.gender = gender;
        this.birthday = birthday;
        this.role = role;
    }

    public MyUser(Long id, String username, String email, String password, String gender, LocalDate birthday, Role role, LocalDateTime created, LocalDateTime updated) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.password = password;
        this.gender = gender;
        this.birthday = birthday;
        this.role = role;
        this.created = LocalDateTime.now();
        this.updated = null;
    }
}
