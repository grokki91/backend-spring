package com.myserver.springserver.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Pattern;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

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

    @Pattern(regexp = "^(MALE|FEMALE)$", message = "Gender must be either 'MALE' or 'FEMALE'")
    public String gender;

    public LocalDate birthday;

    @Enumerated(EnumType.STRING)
    public Role role;

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
}
