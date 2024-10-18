package com.haile.exe101.depairapplication.services;

import com.haile.exe101.depairapplication.models.entity.Sale;
import com.haile.exe101.depairapplication.repositories.SaleRepository;
import com.haile.exe101.depairapplication.services.interfaces.ISaleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SaleService implements ISaleService {

    @Autowired
    private SaleRepository saleRepository;

    @Override
    public Sale createSale(Sale sale) {
        return saleRepository.save(sale);
    }
}
