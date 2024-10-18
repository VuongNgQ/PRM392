package com.haile.exe101.depairapplication.services;

import com.haile.exe101.depairapplication.models.entity.Role;
import com.haile.exe101.depairapplication.models.entity.User;
import com.haile.exe101.depairapplication.models.enums.ERole;
import com.haile.exe101.depairapplication.models.request.SignupRequest;
import com.haile.exe101.depairapplication.repositories.RoleRepository;
import com.haile.exe101.depairapplication.repositories.UserRepository;
import com.haile.exe101.depairapplication.services.interfaces.IUserService;
import com.haile.exe101.depairapplication.utils.SpeedSMSUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Optional;
import java.util.Random;
import java.util.Set;

@Service
public class UserService implements IUserService {
    @Value("${haile.app.smsAccessToken}")
    private String smsAccessToken;

    @Value("${haile.app.senderId}")
    private String senderId;

    @Value("${admin.username}")
    private String adminUserName;

    @Value("${admin.phoneNumber}")
    private String adminPhoneNumber;

    @Value("${admin.password}")
    private String adminPassword;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    PasswordEncoder encoder;

    @Override
    public Boolean existsByPhoneNumber(String phoneNumber) {
        return userRepository.existsByPhoneNumber(phoneNumber);
    }

    @Override
    public Boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }

    @Override
    public Boolean existsById(Long id) {
        return userRepository.existsById(id);
    }

    @Override
    public Boolean registerUser(SignupRequest signupRequest) {
        User user = new User();
        user.setEmail(signupRequest.getEmail());
        user.setPassword(encoder.encode(signupRequest.getPassword()));
        user.setPhoneNumber(signupRequest.getPhoneNumber());
        user.setUserStatus(false);

        Set<Role> roles = new HashSet<>();
        Role role = roleRepository.findByName(ERole.ROLE_USER).orElse(null);
        if (role == null) {
            return false;
        }
        roles.add(role);
        user.setRoles(roles);

        Random r = new Random();
        String randomNumber = String.format("%04d", r.nextInt(10000));
        user.setOtpVerification(Integer.parseInt(randomNumber));
        user.setOtpExpirationTime(LocalDateTime.now().plusMinutes(30)); // 30 phút verification

        User newUser = userRepository.save(user);

        // Gửi SMS cho user phone number
        SpeedSMSUtils api = new SpeedSMSUtils(smsAccessToken);
        try {
            String result = api.sendSMS(signupRequest.getPhoneNumber(), "Chào bạn, mã xác thực của bạn là " + newUser.getOtpVerification(), 5, senderId);
            System.out.println(result);
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    @Override
    public Boolean createAdminUser() {
        User user = new User();
        user.setEmail(adminUserName);
        user.setPassword(encoder.encode(adminPassword));
        user.setPhoneNumber(adminPhoneNumber);
        user.setUserStatus(true);

        Set<Role> roles = new HashSet<>();
        Role role = roleRepository.findByName(ERole.ROLE_ADMIN).orElse(null);
        if (role == null) {
            return false;
        }
        roles.add(role);
        user.setRoles(roles);

        userRepository.save(user);

        return true;
    }

    @Override
    public Boolean verifyUser(String phoneNumber, Integer otp) {
        Optional<User> u = userRepository.findByPhoneNumber(phoneNumber);
        if (u.isPresent()) {
            User user = u.get();
            if (otp == user.getOtpVerification()) {
                if (user.getOtpExpirationTime().isAfter(LocalDateTime.now())) {
                    user.setUserStatus(true);
                    user.setOtpVerification(0);
                    userRepository.save(user);
                    return true;
                }
                return false;
            }
            return false;
        }
        return false;
    }

    @Override
    public Boolean resendOtp(String phoneNumber) {
        User user = userRepository.findByPhoneNumber(phoneNumber).orElse(null);
        if (user != null) {
            Random r = new Random();
            String randomNumber = String.format("%04d", r.nextInt(10000));
            user.setOtpVerification(Integer.parseInt(randomNumber));
            user.setOtpExpirationTime(LocalDateTime.now().plusMinutes(30)); // 30 phút verification

            userRepository.save(user);

            // Gửi SMS cho user phone number
            SpeedSMSUtils api = new SpeedSMSUtils(smsAccessToken);
            try {
                String result = api.sendSMS(phoneNumber, "Chào bạn, mã xác thực của bạn là " + randomNumber, 5, senderId);
                System.out.println(result);
                if (result.contains("error")) {
                    return false;
                }
            } catch (IOException e) {
                e.printStackTrace();
                return false;
            }
            return true;
        }
        return false;
    }

    @Override
    public User findUserByPhoneNumber(String phoneNumber) {
        return userRepository.findByPhoneNumber(phoneNumber).orElse(null);
    }

    @Override
    public User findUserById(Long id) {
        return userRepository.findById(id).orElse(null);
    }
}
