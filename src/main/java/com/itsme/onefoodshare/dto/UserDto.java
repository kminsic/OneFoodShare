package com.itsme.onefoodshare.dto;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {
    private Long userId;
    private String username;
    private String password;
    private String userEmail;
    private String location;


}
