package com.itsme.onefoodshare.entity;

import com.sun.istack.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Getter
@Entity
@NoArgsConstructor
public class RefreshToken {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotNull
    private String refreshToken;
    @NotNull
    private String email;

    public RefreshToken(String token, String email) {
        this.refreshToken = token;
        this.email = email;
    }


    public RefreshToken updateToken(String token) {
        this.refreshToken = token;
        return this;
    }
}