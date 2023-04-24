package com.itsme.onefoodshare.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class TokenDto {

    private String accessToken;
    private String refreshToken;


}
