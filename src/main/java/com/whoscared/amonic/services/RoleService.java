package com.whoscared.amonic.services;

import com.whoscared.amonic.domain.person.Role;
import com.whoscared.amonic.domain.person.TypeOfRole;
import com.whoscared.amonic.repositories.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RoleService {
    private final RoleRepository roleRepository;

    @Autowired
    public RoleService(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    public Role user() {
        return roleRepository.findByRole(TypeOfRole.ROLE_USER).orElse(null);
    }

    public Role admin() {
        return roleRepository.findByRole(TypeOfRole.ROLE_ADMIN).orElse(null);
    }
}
