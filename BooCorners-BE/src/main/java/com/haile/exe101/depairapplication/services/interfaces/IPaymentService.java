package com.haile.exe101.depairapplication.services.interfaces;

import com.haile.exe101.depairapplication.models.entity.Payment;

public interface IPaymentService {
    Payment createOrSavePayment(Payment payment);
}
