package com.haile.exe101.depairapplication.services.interfaces;

import com.haile.exe101.depairapplication.models.entity.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface IOrderService {
    Order createOrSaveOrder(Order order);

    Order getOrderByOrderTrackingNumber(String orderTrackingNumber);

    Page<Order> searchOrdersByCustomerInfo(String searchTerm, Pageable pageable);
}
