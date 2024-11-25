package com.myserver.springserver.dto;

import lombok.Data;

@Data
public class ChangePasswordRequest {
    public String password;
    public String newPassword;
    public String confirmPassword;
}
