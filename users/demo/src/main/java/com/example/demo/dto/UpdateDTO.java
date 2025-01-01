package com.example.demo.dto;

import com.example.demo.model.Status;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
public class UpdateDTO {

    public String username;

    public String oldPassword;
    public String newPassword;
    public Status status;

    public String getUsername() {
        return username;
    }

    public Status getStatus() {
        return status;
    }

    public String getOldPassword() {
        return oldPassword;
    }

    public String getNewPassword() {
        return newPassword;
    }
}
