package com.haile.exe101.depairapplication.repositories;

import com.haile.exe101.depairapplication.models.entity.ShippingInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ShippingInfoRepository extends JpaRepository<ShippingInfo, Long> {
}
