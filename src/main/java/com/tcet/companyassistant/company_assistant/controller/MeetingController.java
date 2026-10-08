package com.tcet.companyassistant.company_assistant.controller;

import com.tcet.companyassistant.company_assistant.entity.Meeting;
import com.tcet.companyassistant.repository.MeetingRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import jakarta.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/meetings")
@Tag(name = "Meetings", description = "Manage company meetings")
public class MeetingController {
    private static final Logger logger =
        LoggerFactory.getLogger(MeetingController.class);

    private final MeetingRepository meetingRepository;

    public MeetingController(MeetingRepository meetingRepository) {
        this.meetingRepository = meetingRepository;
    }

   @GetMapping
public List<Meeting> getAllMeetings() {
    logger.info("Fetching all meetings");

    List<Meeting> meetings = meetingRepository.findAll();

    logger.info("Found {} meetings", meetings.size());

    return meetings;
}

    @GetMapping("/{id}")
public ResponseEntity<Meeting> getMeetingById(
        @PathVariable Integer id) {

    logger.info("Fetching meeting with ID: {}", id);

    return meetingRepository.findById(id)
            .map(meeting -> {
                logger.info("Meeting found with ID: {}", id);
                return ResponseEntity.ok(meeting);
            })
            .orElseGet(() -> {
                logger.warn("Meeting not found with ID: {}", id);
                return ResponseEntity.notFound().build();
            });
}

    @PostMapping
    public Meeting createMeeting(
            @Valid @RequestBody Meeting meeting) {

        return meetingRepository.save(meeting);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Meeting> updateMeeting(
            @PathVariable Integer id,
            @Valid @RequestBody Meeting meetingDetails) {

        return meetingRepository.findById(id)
                .map(meeting -> {

                    meeting.setTitle(meetingDetails.getTitle());
                    meeting.setStartTime(meetingDetails.getStartTime());
                    meeting.setEndTime(meetingDetails.getEndTime());
                    meeting.setLocation(meetingDetails.getLocation());
                    meeting.setOrganizerId(meetingDetails.getOrganizerId());
                    meeting.setCreatedAt(meetingDetails.getCreatedAt());

                    return ResponseEntity.ok(
                            meetingRepository.save(meeting)
                    );
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Meeting> patchMeeting(
            @PathVariable Integer id,
            @RequestBody Meeting meetingDetails) {

        return meetingRepository.findById(id)
                .map(meeting -> {

                    if (meetingDetails.getTitle() != null) {
                        meeting.setTitle(meetingDetails.getTitle());
                    }

                    if (meetingDetails.getStartTime() != null) {
                        meeting.setStartTime(meetingDetails.getStartTime());
                    }

                    if (meetingDetails.getEndTime() != null) {
                        meeting.setEndTime(meetingDetails.getEndTime());
                    }

                    if (meetingDetails.getLocation() != null) {
                        meeting.setLocation(meetingDetails.getLocation());
                    }

                    if (meetingDetails.getOrganizerId() != null) {
                        meeting.setOrganizerId(meetingDetails.getOrganizerId());
                    }

                    if (meetingDetails.getCreatedAt() != null) {
                        meeting.setCreatedAt(meetingDetails.getCreatedAt());
                    }

                    return ResponseEntity.ok(
                            meetingRepository.save(meeting)
                    );
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMeeting(
            @PathVariable Integer id) {

        if (meetingRepository.existsById(id)) {
            meetingRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.notFound().build();
    }
}
