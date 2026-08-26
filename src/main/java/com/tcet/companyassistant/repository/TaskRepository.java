package com.tcet.companyassistant.repository;

import com.tcet.companyassistant.company_assistant.entity.Task;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TaskRepository extends JpaRepository<Task, Integer> {
}