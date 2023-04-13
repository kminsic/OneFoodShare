package com.itsme.onefoodshare.controller;

import com.itsme.onefoodshare.dto.LoginRequestDto;
import com.itsme.onefoodshare.dto.UserDto;
import com.itsme.onefoodshare.service.RegisterService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/api/auth")
public class UserController {
    private final RegisterService registerService;

    public UserController(RegisterService registerService) {
        this.registerService = registerService;
    }

    @PostMapping("/signup")
    public ResponseEntity<?> signUp(@RequestBody UserDto userDto) {
        registerService.signUp(userDto);
        return ResponseEntity.ok("회원가입에 성공했습니다");
    }
    @PostMapping("/login")
    public ResponseEntity<?> logIn(@RequestBody LoginRequestDto logInfo, HttpServletResponse httpServletResponse) {
        registerService.login(logInfo, httpServletResponse);
        return ResponseEntity.ok("ok");
    }

}
