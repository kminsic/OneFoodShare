package com.itsme.onefoodshare.controller;

import com.itsme.onefoodshare.dto.requestDto.LoginRequestDto;
import com.itsme.onefoodshare.dto.requestDto.UserDto;
import com.itsme.onefoodshare.dto.responseDto.GlobalResDto;
import com.itsme.onefoodshare.service.RegisterService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/api/auth")
@AllArgsConstructor
public class UserController {
    private final RegisterService registerService;


    @PostMapping("/signup")
    public ResponseEntity<?> signUp(@RequestBody UserDto userDto) {
        registerService.signup(userDto);
        return ResponseEntity.ok("회원가입에 성공했습니다");
    }
    @PostMapping("/login")
    public GlobalResDto logIn(@RequestBody LoginRequestDto logInfo, HttpServletResponse httpServletResponse) {

        return registerService.login(logInfo, httpServletResponse);
    }

}
