package com.haile.exe101.depairapplication.controllers;

import com.haile.exe101.depairapplication.models.entity.User;
import com.haile.exe101.depairapplication.models.request.LoginRequest;
import com.haile.exe101.depairapplication.models.request.SignupRequest;
import com.haile.exe101.depairapplication.models.response.BaseResponse;
import com.haile.exe101.depairapplication.models.response.JwtResponse;
import com.haile.exe101.depairapplication.repositories.RoleRepository;
import com.haile.exe101.depairapplication.repositories.UserRepository;
import com.haile.exe101.depairapplication.security.JwtUtils;
import com.haile.exe101.depairapplication.services.RoleService;
import com.haile.exe101.depairapplication.services.UserDetailsImpl;
import com.haile.exe101.depairapplication.services.UserService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/auth")
public class AuthController {
    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    UserService userService;

    @Autowired
    RoleService roleService;

    @Autowired
    JwtUtils jwtUtils;

    @Operation(summary = "Authorization Sign in",
            description = "Sign in by input phone number and password to get JWT token")
    @PostMapping("/sign-in")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {
        User user = userService.findUserByPhoneNumber(loginRequest.getPhoneNumber());
        if (user == null) {
            return ResponseEntity.status(404).body(new BaseResponse<>(404, "Tai khoan khong tim thay", null, null));
        }
        if (!user.isUserStatus()) {
            return ResponseEntity.badRequest().body(new BaseResponse<>(400, "Tai khoan chua xac minh", null, null));
        }
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getPhoneNumber(), loginRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        List<String> roles = userDetails.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList());

        return ResponseEntity.ok(new JwtResponse(jwt,
                userDetails.getId(),
                userDetails.getUsername(),
                userDetails.getEmail(),
                roles));
    }

    @Operation(summary = "Authorization Sign up",
            description = "Sign up by inputting phone number, password, email to verify phone Number")
    @PostMapping("/sign-up")
    public ResponseEntity<BaseResponse<String>> signUpUser(@Valid @RequestBody SignupRequest signupRequest) {
        if (userService.existsByPhoneNumber(signupRequest.getPhoneNumber())) {
            return ResponseEntity.badRequest().body(new BaseResponse<>(400, "This phone number has already taken", null, null));
        }

        if (userService.existsByEmail(signupRequest.getEmail())) {
            return ResponseEntity.badRequest().body(new BaseResponse<>(400, "This email already exists", null, null));
        }

        // Create new user's account
        if (!userService.registerUser(signupRequest)) {
            return ResponseEntity.badRequest().body(new BaseResponse<>(400, "Some unexpected errors happened, please try again", null, null));
        }

        return ResponseEntity.ok(new BaseResponse<>(200, "Registered successfully, please verify phone number", null, null));
    }

    @Operation(summary = "Verify phone number",
            description = "Verify phone number to registered account")
    @PostMapping("/verify/{phone-number}/{otp}")
    public ResponseEntity<BaseResponse<String>> verifyPhoneNumber(@PathVariable("phone-number") String phoneNumber, @PathVariable("otp") Integer otp) {
        if (!userService.existsByPhoneNumber(phoneNumber)) {
            return ResponseEntity.status(404).body(new BaseResponse<>(404, "The account with this phone number does not exist", null, null));
        }

        if (!userService.verifyUser(phoneNumber, otp)) {
            return ResponseEntity.badRequest().body(new BaseResponse<>(404, "Cannot verify your account", null, null));
        }

        return ResponseEntity.ok(new BaseResponse<>(200, "Verify phone number successful", null, null));
    }

    @Operation(summary = "Re-verify phone number",
            description = "Resend OTP to phone number")
    @PostMapping("/re-verify/{phone-number}")
    public ResponseEntity<BaseResponse<String>> resendOtpVerification(@PathVariable("phone-number") String phoneNumber) {
        if (!userService.existsByPhoneNumber(phoneNumber)) {
            return ResponseEntity.status(404).body(new BaseResponse<>(404, "The account with this phone number does not exist", null, null));
        }

        if (!userService.resendOtp(phoneNumber)) {
            return ResponseEntity.badRequest().body(new BaseResponse<>(400, "Errors: Cannot send otp to phone number", null, null));
        }

        return ResponseEntity.ok(new BaseResponse<>(200, "Resend otp verification successfully", null, null));
    }
}
