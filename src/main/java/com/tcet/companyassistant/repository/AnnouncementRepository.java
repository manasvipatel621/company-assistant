package com.tcet.companyassistant.repository;

import com.tcet.companyassistant.company_assistant.entity.Announcement;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AnnouncementRepository extends JpaRepository<Announcement, Integer> {
}