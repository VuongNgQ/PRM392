package com.haile.exe101.depairapplication.services;

import com.haile.exe101.depairapplication.models.entity.Order;
import com.haile.exe101.depairapplication.repositories.OrderRepository;
import com.haile.exe101.depairapplication.services.interfaces.IOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class OrderService implements IOrderService {
    @Autowired
    private OrderRepository orderRepository;

    @Override
    public Order createOrSaveOrder(Order order) {
        return orderRepository.save(order);
    }
    @Override
    public Order getOrderByOrderTrackingNumber(String orderTrackingNumber) {
        return orderRepository.findOrderByOrderTrackingNumber(orderTrackingNumber);
    }
    @Override
    public Page<Order> searchOrdersByCustomerInfo(String searchTerm, Pageable pageable) {
        return orderRepository.findByCustomerInfoFullNameContainingOrCustomerInfoEmailContaining(searchTerm, searchTerm, pageable);
    }
}
