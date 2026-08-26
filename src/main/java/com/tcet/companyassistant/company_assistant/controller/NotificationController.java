package com.tcet.companyassistant.company_assistant.controller;

import com.tcet.companyassistant.company_assistant.entity.Notification;
import com.tcet.companyassistant.repository.NotificationRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.List;

@RestController
@RequestMapping("/api/notifications")
public class NotificationController {
private static final Logger logger =
        LoggerFactory.getLogger(NotificationController.class);
    private final NotificationRepository notificationRepository;

    public NotificationController(NotificationRepository notificationRepository) {
        this.notificationRepository = notificationRepository;
    }

    @GetMapping
public List<Notification> getAllNotifications() {
    logger.info("Fetching all notifications");

    List<Notification> notifications = notificationRepository.findAll();

    logger.info("Found {} notifications", notifications.size());

    return notifications;
}

  @GetMapping("/{id}")
public ResponseEntity<Notification> getNotificationById(
        @PathVariable Integer id) {

    logger.info("Fetching notification with ID: {}", id);

    return notificationRepository.findById(id)
            .map(notification -> {
                logger.info("Notification found with ID: {}", id);
                return ResponseEntity.ok(notification);
            })
            .orElseGet(() -> {
                logger.warn("Notification not found with ID: {}", id);
                return ResponseEntity.notFound().build();
            });
}

    @PostMapping
    public Notification createNotification(
            @RequestBody Notification notification) {

        return notificationRepository.save(notification);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Notification> updateNotification(
            @PathVariable Integer id,
            @RequestBody Notification notificationDetails) {

        return notificationRepository.findById(id)
                .map(notification -> {

                    notification.setMessage(notificationDetails.getMessage());
                    notification.setType(notificationDetails.getType());
                    notification.setIsRead(notificationDetails.getIsRead());
                    notification.setUserId(notificationDetails.getUserId());
                    notification.setCreatedAt(notificationDetails.getCreatedAt());

                    return ResponseEntity.ok(
                            notificationRepository.save(notification)
                    );
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Notification> patchNotification(
            @PathVariable Integer id,
            @RequestBody Notification notificationDetails) {

        return notificationRepository.findById(id)
                .map(notification -> {

                    if (notificationDetails.getMessage() != null) {
                        notification.setMessage(notificationDetails.getMessage());
                    }

                    if (notificationDetails.getType() != null) {
                        notification.setType(notificationDetails.getType());
                    }

                    if (notificationDetails.getIsRead() != null) {
                        notification.setIsRead(notificationDetails.getIsRead());
                    }

                    if (notificationDetails.getUserId() != null) {
                        notification.setUserId(notificationDetails.getUserId());
                    }

                    if (notificationDetails.getCreatedAt() != null) {
                        notification.setCreatedAt(notificationDetails.getCreatedAt());
                    }

                    return ResponseEntity.ok(
                            notificationRepository.save(notification)
                    );
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteNotification(
            @PathVariable Integer id) {

        if (notificationRepository.existsById(id)) {
            notificationRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.notFound().build();
    }
}