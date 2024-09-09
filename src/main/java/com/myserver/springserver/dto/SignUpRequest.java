package com.myserver.springserver.dto;

import lombok.Data;

@Data
public class SignUpRequest {
    public String username;
    public String password;
    public String email;
}
