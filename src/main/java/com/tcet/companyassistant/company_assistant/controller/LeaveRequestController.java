package com.tcet.companyassistant.company_assistant.controller;

import com.tcet.companyassistant.company_assistant.entity.LeaveRequest;
import com.tcet.companyassistant.repository.LeaveRequestRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import jakarta.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/leave-requests")
@Tag(name = "Leave requests", description = "Manage employee leave requests")
public class LeaveRequestController {
    private static final Logger logger =
        LoggerFactory.getLogger(LeaveRequestController.class);

    private final LeaveRequestRepository leaveRequestRepository;

    public LeaveRequestController(LeaveRequestRepository leaveRequestRepository) {
        this.leaveRequestRepository = leaveRequestRepository;
    }

   @GetMapping
public List<LeaveRequest> getAllLeaveRequests() {
    logger.info("Fetching all leave requests");

    List<LeaveRequest> leaveRequests = leaveRequestRepository.findAll();

    logger.info("Found {} leave requests", leaveRequests.size());

    return leaveRequests;
}

   @GetMapping("/{id}")
public ResponseEntity<LeaveRequest> getLeaveRequestById(
        @PathVariable Integer id) {

    logger.info("Fetching leave request with ID: {}", id);

    return leaveRequestRepository.findById(id)
            .map(leaveRequest -> {
                logger.info("Leave request found with ID: {}", id);
                return ResponseEntity.ok(leaveRequest);
            })
            .orElseGet(() -> {
                logger.warn("Leave request not found with ID: {}", id);
                return ResponseEntity.notFound().build();
            });
}
    @PostMapping
    public LeaveRequest createLeaveRequest(
            @Valid @RequestBody LeaveRequest leaveRequest) {

        return leaveRequestRepository.save(leaveRequest);
    }

    @PutMapping("/{id}")
    public ResponseEntity<LeaveRequest> updateLeaveRequest(
            @PathVariable Integer id,
            @Valid @RequestBody LeaveRequest details) {

        return leaveRequestRepository.findById(id)
                .map(leaveRequest -> {

                    leaveRequest.setLeaveType(details.getLeaveType());
                    leaveRequest.setStartDate(details.getStartDate());
                    leaveRequest.setEndDate(details.getEndDate());
                    leaveRequest.setStatus(details.getStatus());
                    leaveRequest.setUserId(details.getUserId());
                    leaveRequest.setApprovedBy(details.getApprovedBy());
                    leaveRequest.setCreatedAt(details.getCreatedAt());

                    return ResponseEntity.ok(
                            leaveRequestRepository.save(leaveRequest)
                    );
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @PatchMapping("/{id}")
    public ResponseEntity<LeaveRequest> patchLeaveRequest(
            @PathVariable Integer id,
            @RequestBody LeaveRequest details) {

        return leaveRequestRepository.findById(id)
                .map(leaveRequest -> {

                    if (details.getLeaveType() != null) {
                        leaveRequest.setLeaveType(details.getLeaveType());
                    }

                    if (details.getStartDate() != null) {
                        leaveRequest.setStartDate(details.getStartDate());
                    }

                    if (details.getEndDate() != null) {
                        leaveRequest.setEndDate(details.getEndDate());
                    }

                    if (details.getStatus() != null) {
                        leaveRequest.setStatus(details.getStatus());
                    }

                    if (details.getUserId() != null) {
                        leaveRequest.setUserId(details.getUserId());
                    }

                    if (details.getApprovedBy() != null) {
                        leaveRequest.setApprovedBy(details.getApprovedBy());
                    }

                    if (details.getCreatedAt() != null) {
                        leaveRequest.setCreatedAt(details.getCreatedAt());
                    }

                    return ResponseEntity.ok(
                            leaveRequestRepository.save(leaveRequest)
                    );
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteLeaveRequest(
            @PathVariable Integer id) {

        if (leaveRequestRepository.existsById(id)) {
            leaveRequestRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.notFound().build();
    }
}
