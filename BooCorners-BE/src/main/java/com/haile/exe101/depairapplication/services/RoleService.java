package com.haile.exe101.depairapplication.services;

import com.haile.exe101.depairapplication.models.entity.Role;
import com.haile.exe101.depairapplication.models.enums.ERole;
import com.haile.exe101.depairapplication.repositories.RoleRepository;
import com.haile.exe101.depairapplication.services.interfaces.IRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RoleService implements IRoleService {
    @Autowired
    private RoleRepository roleRepository;

    @Override
    public Role findByRoleName(ERole roleName) {
        return roleRepository.findByName(roleName).orElse(null);
    }
}
