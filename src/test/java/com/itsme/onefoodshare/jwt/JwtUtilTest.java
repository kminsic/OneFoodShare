package com.itsme.onefoodshare.jwt;

import com.itsme.onefoodshare.Repository.RefreshTokenRepository;
import com.itsme.onefoodshare.dto.TokenDto;
import com.itsme.onefoodshare.service.UserDetailsServiceImpl;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class JwtUtilTest {

    @Mock
    private RefreshTokenRepository refreshTokenRepository;

    @Mock
    private UserDetailsServiceImpl userDetailsService;

    @InjectMocks
    private JwtUtil jwtUtil;

    @Test
    @DisplayName("Should create access and refresh tokens for the given email")
    void createAllTokenForGivenEmail() {
        String email = "test@example.com";

        TokenDto tokenDto = jwtUtil.createAllToken(email);

        assertNotNull(tokenDto);
        assertNotNull(tokenDto.getAccessToken());
        assertNotNull(tokenDto.getRefreshToken());
        assertEquals(email, jwtUtil.getEmailFromToken(tokenDto.getAccessToken()));
        assertEquals(email, jwtUtil.getEmailFromToken(tokenDto.getRefreshToken()));
        assertTrue(jwtUtil.tokenValidation(tokenDto.getAccessToken()));
        assertTrue(jwtUtil.tokenValidation(tokenDto.getRefreshToken()));
    }
}