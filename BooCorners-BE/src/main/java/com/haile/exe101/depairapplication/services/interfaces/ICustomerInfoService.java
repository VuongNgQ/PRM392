package com.haile.exe101.depairapplication.services.interfaces;

import com.haile.exe101.depairapplication.models.entity.CustomerInfo;
import com.haile.exe101.depairapplication.models.request.CustomerInfoRequest;
import com.haile.exe101.depairapplication.models.response.BaseResponse;

public interface ICustomerInfoService {
    BaseResponse<CustomerInfo> createCustomerInfo(CustomerInfoRequest customerInfoRequest);

    CustomerInfo getCustomerInfo(Long customerId);
}
