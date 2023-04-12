package com.itsme.onefoodshare.jwt;


import com.itsme.onefoodshare.Repository.RefreshTokenRepository;
import com.itsme.onefoodshare.dto.TokenDto;
import com.itsme.onefoodshare.entity.RefreshToken;
import com.itsme.onefoodshare.entity.User;
import com.itsme.onefoodshare.entity.UserDetailsImpl;
import com.itsme.onefoodshare.shared.Authority;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SecurityException;

import java.nio.file.AccessDeniedException;
import java.security.Key;
import java.util.Date;
import java.util.Optional;


import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;

@Slf4j
@Component
public class TokenProvider {

    private static final String AUTHORITIES_KEY = "auth";
    private static final String BEARER_PREFIX = "BEARER ";
    private static final long ACCESS_TOKEN_EXPIRE_TIME = 1000 * 60 * 30;            //30분
    private static final long REFRESH_TOKEN_EXPRIRE_TIME = 1000 * 60 * 60 * 24 * 7;     //7일
    private final Key key;
    private final RefreshTokenRepository refreshTokenRepository;

    public TokenProvider(@Value("${jwt.secret}") String secretKey,
                         RefreshTokenRepository refreshTokenRepository) {
        this.refreshTokenRepository = refreshTokenRepository;
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        this.key = Keys.hmacShaKeyFor(keyBytes);
    }

    public TokenDto generateTokenDto(UserDetailsImpl userDetails) {
        long now = (new Date().getTime());

        // access token 생성 (사용자 정보를 복호화)
        Date accessTokenExpiresIn = new Date(now + ACCESS_TOKEN_EXPIRE_TIME);
        String accessToken = Jwts.builder()
                .setSubject(userDetails.getUsername())                       // payload "sub": "memberId"
                .claim(AUTHORITIES_KEY, Authority.ROLE_MEMBER.toString())   // payload "auth" : "ROLE_USER
                .setExpiration(accessTokenExpiresIn)                        // payload "exp" : 만료시간 설정
                .signWith(key, SignatureAlgorithm.HS256)                    // header "alg" : "HS512"
                .compact();
        // refresh token 생성
        String refreshToken = Jwts.builder()
                .setExpiration(new Date(now + REFRESH_TOKEN_EXPRIRE_TIME))
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();

        RefreshToken refreshTokenObject = RefreshToken.builder()
                .id(userDetails.getId())
                .user(userDetails.getUser())
                .keyValue(refreshToken)
                .build();

        refreshTokenRepository.save(refreshTokenObject); // refreshToken 저장

        return TokenDto.builder()
                .grantType(BEARER_PREFIX)
                .accessToken(accessToken)
                .accessTokenExpiresIn(accessTokenExpiresIn.getTime())
                .refreshToken(refreshToken)
                .build();
    }

    public UserDetailsImpl getMemberFromAuthentication() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || AnonymousAuthenticationToken.class.
                isAssignableFrom(authentication.getClass())) {
            return null;
        }
        return (UserDetailsImpl) authentication.getPrincipal();
    }

    // 토큰 유효성 검증
    @SneakyThrows // 명시적인 예외처리를 할 수 있음(불가능한 예외처리)
    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
            return true;
        } catch (SecurityException | MalformedJwtException e) {
            log.info("Invalid JWT signature, 유효하지 않는 JWT 서명 입니다.");
        } catch (ExpiredJwtException e) {
            log.info("Expired JWT token, 만료된 JWT token 입니다.");
            throw new AccessDeniedException("access token이 만료되었습니다.");
        } catch (UnsupportedJwtException e) {
            log.info("Unsupported JWT token, 지원되지 않는 JWT 토큰 입니다.");
        } catch (IllegalArgumentException e) {
            log.info("JWT claims is empty, 잘못된 JWT 토큰 입니다.");
        }
        return false;
    }

    @Transactional(readOnly = true) // 1. 해당 트랜잭션 내에서 데이터를 읽기만 함 2. Hibernate를 사용하는 경우 속도향상 효과 3. 의도치않게 데이터를 변경하는 것을 막아줌
    public String validateRefreshToken(HttpServletRequest request) {
        try {
            RefreshToken token = refreshTokenRepository.findByKeyValue(request.getHeader("RefreshToken"));

            long now = (new Date().getTime());

            // access token 생성 (사용자 정보를 복호화)
            Date accessTokenExpiresIn = new Date(now + ACCESS_TOKEN_EXPIRE_TIME);
            String accessToken = Jwts.builder()
                    .setSubject(token.getUser().getUsername())                // payload "sub": "memberId"
                    .claim(AUTHORITIES_KEY, Authority.ROLE_MEMBER.toString())   // payload "auth" : "ROLE_USER
                    .setExpiration(accessTokenExpiresIn)                        // payload "exp" : 만료시간 설정
                    .signWith(key, SignatureAlgorithm.HS256)                    // header "alg" : "HS512"
                    .compact();
            return BEARER_PREFIX + accessToken;

        } catch (ExpiredJwtException e) {
            log.info("만료된 JWT token 입니다.");
        } catch (NullPointerException e) {
            log.info("로그인이 필요합니다.");
        }
        return null;

    }

    @Transactional(readOnly = true)
    public RefreshToken isPresentRefreshToken(User user) {
        Optional<RefreshToken> optionalRefreshToken = refreshTokenRepository.findByMember(user);
        return optionalRefreshToken.orElse(null);
    }

    @Transactional
    public ResponseEntity deleteRefreshToken(User user) {
        RefreshToken refreshToken = isPresentRefreshToken(user);
        if (null == refreshToken) {
            return new ResponseEntity("TOKEN_NOT_FOUND", HttpStatus.valueOf("존재하지 않는 Token 입니다."));
        }
        refreshTokenRepository.delete(refreshToken);
        return new ResponseEntity(HttpStatus.OK);
    }
}