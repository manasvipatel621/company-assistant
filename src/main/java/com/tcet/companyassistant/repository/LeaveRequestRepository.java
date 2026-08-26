package com.tcet.companyassistant.repository;

import com.tcet.companyassistant.company_assistant.entity.LeaveRequest;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LeaveRequestRepository extends JpaRepository<LeaveRequest, Integer> {
}