package com.itsme.onefoodshare.service;


import com.itsme.onefoodshare.Repository.RefreshTokenRepository;
import com.itsme.onefoodshare.Repository.UserRepository;
import com.itsme.onefoodshare.dto.responseDto.GlobalResDto;
import com.itsme.onefoodshare.dto.requestDto.LoginRequestDto;
import com.itsme.onefoodshare.dto.TokenDto;

import com.itsme.onefoodshare.dto.requestDto.UserDto;
import com.itsme.onefoodshare.entity.RefreshToken;
import com.itsme.onefoodshare.entity.User;
import com.itsme.onefoodshare.jwt.JwtUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletResponse;
import java.util.Optional;


@Service
@Slf4j
@RequiredArgsConstructor
public class RegisterService {
    private final JwtUtil jwtUtil;
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final RefreshTokenRepository refreshTokenRepository;

    @Transactional
    public GlobalResDto signup(UserDto userDto) {
        // email 중복검사
        if(userRepository.findByEmail(userDto.getEmail()).isPresent()){
            throw new RuntimeException("중복된 이메일입니다.");
        }

        // 패스워드 암호화

        User user = User.builder()
                .email(userDto.getEmail())
                .username(userDto.getUsername())
                .password(passwordEncoder.encode(userDto.getPassword()))
                .build();
        // 회원가입 성공
        userRepository.save(user);
        return new GlobalResDto("Success signup",HttpStatus.OK.value());
    }

    @Transactional
    public GlobalResDto login(LoginRequestDto loginReqDto, HttpServletResponse response) {

        // 아이디 검사
        User user = userRepository.findByEmail(loginReqDto.getEmail()).orElseThrow(
                () -> new RuntimeException("Not found Account")
        );
        // 비밀번호 검사
        if(!passwordEncoder.matches(loginReqDto.getPassword(), user.getPassword())) {
            throw new RuntimeException("Not matches Password");
        }

        // 아이디 정보로 Token생성
        TokenDto tokenDto = jwtUtil.createAllToken(loginReqDto.getEmail());
        // Refresh토큰 있는지 확인
        Optional<RefreshToken> refreshToken = refreshTokenRepository.findByEmail(loginReqDto.getEmail());

        if(refreshToken.isPresent()) {
            refreshTokenRepository.save(refreshToken.get().updateToken(tokenDto.getRefreshToken()));
        }else {
            RefreshToken newToken = new RefreshToken(tokenDto.getRefreshToken(), loginReqDto.getEmail());
            refreshTokenRepository.save(newToken);
        }

        // response 헤더에 Access Token / Refresh Token 넣음
        setHeader(response,tokenDto);

        return new GlobalResDto("Success Login", HttpStatus.OK.value());

    }

    private void setHeader(HttpServletResponse response, TokenDto tokenDto) {
        response.addHeader(JwtUtil.ACCESS_TOKEN, tokenDto.getAccessToken());
        response.addHeader(JwtUtil.REFRESH_TOKEN, tokenDto.getRefreshToken());
    }



}
