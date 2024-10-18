package com.haile.exe101.depairapplication.controllers.api;

import com.haile.exe101.depairapplication.models.entity.CustomerInfo;
import com.haile.exe101.depairapplication.models.request.CustomerInfoRequest;
import com.haile.exe101.depairapplication.models.response.BaseResponse;
import com.haile.exe101.depairapplication.services.interfaces.ICustomerInfoService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/customer-info")
public class CustomerInfoController {
    @Autowired
    private ICustomerInfoService customerInfoService;
    // Nhap thong tin khach hang
    @Operation(summary = "Input customer information",
            description = "Create Customer info by requesting email")
    @PostMapping
    public ResponseEntity<BaseResponse<CustomerInfo>> createCustomerInfo(@RequestBody @Valid CustomerInfoRequest req) {
        return ResponseEntity.ok().body(customerInfoService.createCustomerInfo(req));
    }
}
