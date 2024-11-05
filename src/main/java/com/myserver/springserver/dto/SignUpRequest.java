package com.myserver.springserver.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class SignUpRequest {
    public String username;
    public String password;
    public String email;
    public String gender;
    public LocalDate birthday;
    public Boolean isAdmin;
}
