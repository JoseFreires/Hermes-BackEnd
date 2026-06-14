package com.hermes.projeto.backend.repository;

import com.hermes.projeto.backend.domain.ContaAdm;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;

public interface ContaAdmRepository  extends JpaRepository<ContaAdm, Long> {
    UserDetails findByUsername(String username);
}
