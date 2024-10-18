package com.haile.exe101.depairapplication.services;

import com.haile.exe101.depairapplication.models.entity.Payment;
import com.haile.exe101.depairapplication.repositories.PaymentRepository;
import com.haile.exe101.depairapplication.services.interfaces.IPaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PaymentService implements IPaymentService {
    @Autowired
    private PaymentRepository paymentRepository;

    @Override
    public Payment createOrSavePayment(Payment payment) {
        return paymentRepository.save(payment);
    }
}
