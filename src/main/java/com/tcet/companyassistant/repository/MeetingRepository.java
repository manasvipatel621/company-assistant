package com.tcet.companyassistant.repository;

import com.tcet.companyassistant.company_assistant.entity.Meeting;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MeetingRepository extends JpaRepository<Meeting, Integer> {
}