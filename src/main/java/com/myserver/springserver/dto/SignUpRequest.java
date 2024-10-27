package com.myserver.springserver.dto;

import com.myserver.springserver.model.Gender;
import lombok.Data;

import java.time.LocalDate;

@Data
public class SignUpRequest {
    public String username;
    public String password;
    public String email;
    public Gender gender;
    public LocalDate birthday;
    public Boolean isAdmin;
}
