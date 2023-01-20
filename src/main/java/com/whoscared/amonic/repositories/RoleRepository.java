package com.whoscared.amonic.repositories;

import com.whoscared.amonic.domain.person.Role;
import com.whoscared.amonic.domain.person.TypeOfRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByRole(TypeOfRole role);
}
