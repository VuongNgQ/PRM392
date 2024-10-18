package com.haile.exe101.depairapplication.controllers;

import com.haile.exe101.depairapplication.services.interfaces.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/test")
public class TestController {

    @Autowired
    private IUserService userService;

    @GetMapping
    public String test() {
        return "<h1>Hello World</h1>";
    }

    @PostMapping
    public ResponseEntity<String> createAdminAccount() {
        if(userService.createAdminUser()) {
            return ResponseEntity.ok("<h1>Admin Account Created</h1>");
        }
        return ResponseEntity.ok("Failed to create admin account");
    }
}
