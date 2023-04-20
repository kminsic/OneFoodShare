package com.itsme.onefoodshare.dto.requestDto;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {
    private String username;
    private String password;
    private String email;
    private String location;


    public void setEncodePwd(String encodePwd) {
        this.password = encodePwd;
    }
}
