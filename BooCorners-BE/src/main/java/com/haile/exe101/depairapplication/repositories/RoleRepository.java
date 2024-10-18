package com.haile.exe101.depairapplication.repositories;

import com.haile.exe101.depairapplication.models.entity.Role;
import com.haile.exe101.depairapplication.models.enums.ERole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByName(ERole name);
}
