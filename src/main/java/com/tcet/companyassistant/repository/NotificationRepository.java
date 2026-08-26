package com.tcet.companyassistant.repository;

import com.tcet.companyassistant.company_assistant.entity.Notification;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NotificationRepository extends JpaRepository<Notification, Integer> {
}