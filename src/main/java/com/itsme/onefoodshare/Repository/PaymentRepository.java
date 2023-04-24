package com.itsme.onefoodshare.Repository;

import com.itsme.onefoodshare.entity.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentRepository extends JpaRepository<Payment, Long> {
}