package com.itsme.onefoodshare.Repository;


import com.itsme.onefoodshare.entity.RefreshToken;
import com.itsme.onefoodshare.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {
    Optional<RefreshToken> findByEmail(String email);


}
