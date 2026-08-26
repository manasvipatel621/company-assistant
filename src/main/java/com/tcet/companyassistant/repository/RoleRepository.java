package com.tcet.companyassistant.repository;

import com.tcet.companyassistant.company_assistant.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Integer> {
}