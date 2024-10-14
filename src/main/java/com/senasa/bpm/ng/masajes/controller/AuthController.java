package com.senasa.bpm.ng.masajes.controller;

import com.senasa.bpm.ng.masajes.model.ChangePassword;
import com.senasa.bpm.ng.masajes.model.User;
import com.senasa.bpm.ng.masajes.model.response.ApiResponse;
import com.senasa.bpm.ng.masajes.model.response.LoginReponse;
import com.senasa.bpm.ng.masajes.service.AuthService;
import com.senasa.bpm.ng.masajes.utility.ConstantUtil;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/masajes/auth")
@AllArgsConstructor
public class AuthController {
    @Autowired
    private AuthService authService;

    @PostMapping("login")
    public ResponseEntity<ApiResponse<LoginReponse>> loginUser (@RequestBody User user) {
        return  ResponseEntity.ok(
                ApiResponse.<LoginReponse>builder()
                        .code(ConstantUtil.OK_CODE)
                        .message(ConstantUtil.OK_MESSAGE)
                        .data(authService.loginUser(user))
                        .build()
        );
    }

    @PostMapping("change-password")
    public ResponseEntity<ApiResponse<Void>> changePassword (@RequestBody ChangePassword user) {
        return  ResponseEntity.ok(
                ApiResponse.<Void>builder()
                        .code(ConstantUtil.OK_CODE)
                        .message(ConstantUtil.OK_MESSAGE)
                        .data(authService.changePassword(user))
                        .build()
        );
    }
}
