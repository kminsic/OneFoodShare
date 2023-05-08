package com.itsme.onefoodshare.entity;

import com.itsme.onefoodshare.dto.requestDto.UserDto;
import lombok.*;
import org.springframework.security.crypto.password.PasswordEncoder;


import javax.persistence.*;

@Entity
@Getter
@Setter
@Table(name = "users")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false, unique = true)
    private String email;

    @Column
    private String location;


    public User(UserDto userDto) {
        this.email = userDto.getEmail();
        this.password = userDto.getPassword();
    }

//    public boolean validatePassword(PasswordEncoder passwordEncoder, String password) {
//        return passwordEncoder.matches(password, this.password);
//    }



    // Getters and setters
}
