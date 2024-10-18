package com.haile.exe101.depairapplication.services;

import com.haile.exe101.depairapplication.models.entity.CustomerInfo;
import com.haile.exe101.depairapplication.models.request.CustomerInfoRequest;
import com.haile.exe101.depairapplication.models.response.BaseResponse;
import com.haile.exe101.depairapplication.repositories.CustomerInfoRepository;
import com.haile.exe101.depairapplication.services.interfaces.ICustomerInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CustomerInfoService implements ICustomerInfoService {
    @Autowired
    private CustomerInfoRepository customerInfoRepository;

    @Override
    public BaseResponse<CustomerInfo> createCustomerInfo(CustomerInfoRequest customerInfoRequest) {
        CustomerInfo customerInfo = new CustomerInfo();
        customerInfo.setEmail(customerInfoRequest.getEmail());
        customerInfo.setFullName(customerInfoRequest.getFullName());
        customerInfo.setPhoneNumber(customerInfoRequest.getPhoneNumber());
        customerInfo.setShippingAddress(customerInfoRequest.getShippingAddress());
        customerInfo.setSendMailFlag(customerInfoRequest.isSendMailFlag());
        try {
            CustomerInfo createdCustomerInfo = customerInfoRepository.save(customerInfo);
            return new BaseResponse<>(200, "Created customer info successfully.", null, createdCustomerInfo);
        } catch (Exception ex) {
            return new BaseResponse<>(500, "Create customer info failed.", null, null);
        }
    }

    @Override
    public CustomerInfo getCustomerInfo(Long customerId) {
        return customerInfoRepository.getReferenceById(customerId);
    }
}
