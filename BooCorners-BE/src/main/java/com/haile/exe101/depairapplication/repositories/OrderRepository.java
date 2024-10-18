package com.haile.exe101.depairapplication.repositories;

import com.haile.exe101.depairapplication.models.entity.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    Order findOrderByOrderTrackingNumber(String orderTrackingNumber);

    Page<Order> findByCustomerInfoFullNameContainingOrCustomerInfoEmailContaining(
            String fullNameTerm, String emailTerm, Pageable pageable
    );
}
