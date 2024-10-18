package com.haile.exe101.depairapplication.services.interfaces;

import com.haile.exe101.depairapplication.models.entity.User;
import com.haile.exe101.depairapplication.models.request.SignupRequest;

public interface IUserService {
    Boolean existsByPhoneNumber(String phoneNumber);

    Boolean existsByEmail(String email);

    Boolean existsById(Long id);

    Boolean registerUser(SignupRequest signupRequest);

    Boolean createAdminUser();

    Boolean verifyUser(String phoneNumber, Integer otp);

    Boolean resendOtp(String phoneNumber);

    User findUserByPhoneNumber(String phoneNumber);

    User findUserById(Long id);
}
