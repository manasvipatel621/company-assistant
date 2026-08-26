package com.tcet.companyassistant.repository;

import com.tcet.companyassistant.company_assistant.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Integer> {
}