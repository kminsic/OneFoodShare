package com.itsme.onefoodshare.service;

import com.itsme.onefoodshare.Repository.UserRepository;
import com.itsme.onefoodshare.dto.UserDto;
import com.itsme.onefoodshare.entity.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletResponse;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public User signUp(UserDto userDto) {
        User user = User.builder()
                .username(userDto.getUsername())
                .password(passwordEncoder.encode(userDto.getPassword()))
                .email(userDto.getUserEmail())
                .build();
        user = userRepository.save(user);
        return user;
    }
    @Transactional
    public ResponseEntity<?> login(UserDto userDto, HttpServletResponse response) {
        User user = isPresentMember(userDto.getUsername());
        if (null == user) {
            return ResponseEntity.badRequest().body("유효한 사용자의 이름이 아닙니다");
        }
        if (!user.validatePassword(passwordEncoder, requestDto.getPassword())) {
            return ResponseDto.fail("INVALID_PASSWORD", "비밀번호가 틀렸습니다.");
        }
        UserDetails userDetails = new UserDetailsImpl(member);
        Authentication authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities()); // 사용자 인증
        SecurityContextHolder.getContext().setAuthentication(authentication); //security가 인증한 사용자로 등록

        UserDetailsImpl userDetails1 = (UserDetailsImpl) authentication.getPrincipal(); // UserDetails를 구현한 현재 사용자 정보 가져오기
        TokenDto tokenDto = tokenProvider.generateTokenDto(userDetails1);


        response.addHeader("Authorization", "BEARER" + " " + tokenDto.getAccessToken());
        response.addHeader("RefreshToken", tokenDto.getRefreshToken());
        response.addHeader("Access-Token-Expire-Time", tokenDto.getAccessTokenExpiresIn().toString());
        myPostService.loginNotification(userDetails1);
        return ResponseDto.success(
                MemberResponseDto.builder()
                        .nickname(member.getNickname())
                        .profileImage(member.getProfileImage())
                        .interested(member.getInterested())
                        .build());
    }

    @Transactional(readOnly = true)
    public User isPresentMember(String membername) {
        Optional<User> optionalMember = userRepository.findByUsername(membername);
        return optionalMember.orElse(null);
    }


}