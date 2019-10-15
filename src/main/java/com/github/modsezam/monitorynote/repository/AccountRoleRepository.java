package com.github.modsezam.monitorynote.repository;

import com.github.modsezam.monitorynote.model.AccountRole;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AccountRoleRepository extends JpaRepository<AccountRole, Long> {
    boolean existsByName(String name);

    Optional<AccountRole> findByName(String role);
}
