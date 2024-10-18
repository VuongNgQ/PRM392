package com.haile.exe101.depairapplication.services.interfaces;

import com.haile.exe101.depairapplication.models.entity.Role;
import com.haile.exe101.depairapplication.models.enums.ERole;

public interface IRoleService {
    Role findByRoleName(ERole roleName);
}
