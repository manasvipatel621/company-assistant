package com.tcet.companyassistant.company_assistant.controller;

import com.tcet.companyassistant.company_assistant.entity.Announcement;
import com.tcet.companyassistant.repository.AnnouncementRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import jakarta.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/announcements")
@Tag(name = "Announcements", description = "Manage company announcements")
public class AnnouncementController {
    private static final Logger logger =
        LoggerFactory.getLogger(AnnouncementController.class);

    private final AnnouncementRepository announcementRepository;

    public AnnouncementController(AnnouncementRepository announcementRepository) {
        this.announcementRepository = announcementRepository;
    }
@GetMapping
public List<Announcement> getAllAnnouncements() {
    logger.info("Fetching all announcements");

    List<Announcement> announcements = announcementRepository.findAll();

    logger.info("Found {} announcements", announcements.size());

    return announcements;
}

   @GetMapping("/{id}")
public ResponseEntity<Announcement> getAnnouncementById(
        @PathVariable Integer id) {

    logger.info("Fetching announcement with ID: {}", id);

    return announcementRepository.findById(id)
            .map(announcement -> {
                logger.info("Announcement found with ID: {}", id);
                return ResponseEntity.ok(announcement);
            })
            .orElseGet(() -> {
                logger.warn("Announcement not found with ID: {}", id);
                return ResponseEntity.notFound().build();
            });
}

    @PostMapping
    public Announcement createAnnouncement(
            @Valid @RequestBody Announcement announcement) {

        return announcementRepository.save(announcement);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Announcement> updateAnnouncement(
            @PathVariable Integer id,
            @Valid @RequestBody Announcement announcementDetails) {

        return announcementRepository.findById(id)
                .map(announcement -> {

                    announcement.setTitle(announcementDetails.getTitle());
                    announcement.setContent(announcementDetails.getContent());
                    announcement.setPublishedBy(announcementDetails.getPublishedBy());
                    announcement.setDepartmentId(announcementDetails.getDepartmentId());
                    announcement.setPublishedAt(announcementDetails.getPublishedAt());

                    return ResponseEntity.ok(
                            announcementRepository.save(announcement)
                    );
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Announcement> patchAnnouncement(
            @PathVariable Integer id,
            @RequestBody Announcement announcementDetails) {

        return announcementRepository.findById(id)
                .map(announcement -> {

                    if (announcementDetails.getTitle() != null) {
                        announcement.setTitle(announcementDetails.getTitle());
                    }

                    if (announcementDetails.getContent() != null) {
                        announcement.setContent(announcementDetails.getContent());
                    }

                    if (announcementDetails.getPublishedBy() != null) {
                        announcement.setPublishedBy(announcementDetails.getPublishedBy());
                    }

                    if (announcementDetails.getDepartmentId() != null) {
                        announcement.setDepartmentId(
                                announcementDetails.getDepartmentId()
                        );
                    }

                    if (announcementDetails.getPublishedAt() != null) {
                        announcement.setPublishedAt(
                                announcementDetails.getPublishedAt()
                        );
                    }

                    return ResponseEntity.ok(
                            announcementRepository.save(announcement)
                    );
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAnnouncement(
            @PathVariable Integer id) {

        if (announcementRepository.existsById(id)) {
            announcementRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.notFound().build();
    }
}
