package com.haile.exe101.depairapplication.repositories;

import com.haile.exe101.depairapplication.models.entity.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, Long> {
}
