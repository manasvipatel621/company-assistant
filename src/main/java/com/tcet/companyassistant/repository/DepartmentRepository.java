package com.tcet.companyassistant.repository;

import com.tcet.companyassistant.company_assistant.entity.Department;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DepartmentRepository extends JpaRepository<Department, Integer> {
}