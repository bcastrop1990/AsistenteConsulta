package com.senasa.bpm.ng.masajes.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ChangePassword {
    private String username;
    private String password;
    private String newPassword;
}
