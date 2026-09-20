package com.smartSchool.repositories;

import com.smartSchool.entities.Role;
import com.smartSchool.enums.RoleName;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Long> {

    // Standard enum search
    Optional<Role> findByName(RoleName name);

    // Convenience method using String
    default Optional<Role> findByNameString(String name) {
        try {
            return findByName(RoleName.valueOf(name));
        } catch (IllegalArgumentException | NullPointerException e) {
            return Optional.empty();
        }
    }
}