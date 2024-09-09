package com.myserver.springserver.dto;

import lombok.Data;

@Data
public class SignInRequest {
    public String username;
    public String password;
}
