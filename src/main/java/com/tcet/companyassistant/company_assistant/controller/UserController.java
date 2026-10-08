package com.tcet.companyassistant.company_assistant.controller;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.tcet.companyassistant.company_assistant.entity.User;
import com.tcet.companyassistant.company_assistant.exception.ResourceNotFoundException;
import com.tcet.companyassistant.repository.UserRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import java.util.List;

@RestController
@RequestMapping("/api/users")
@Tag(name = "Users", description = "Manage company users")
public class UserController {
    private static final Logger logger =
        LoggerFactory.getLogger(UserController.class);

    private final UserRepository userRepository;

    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping
    @Operation(summary = "List users", description = "Returns all company users")
public List<User> getAllUsers() {
    logger.info("Fetching all users");

    List<User> users = userRepository.findAll();

    logger.info("Found {} users", users.size());

    return users;
}


    @GetMapping("/{id}")
    @Operation(summary = "Get user", description = "Returns a user by ID")
public User getUserById(@PathVariable Integer id) {

    logger.info("Fetching user with ID: {}", id);

    return userRepository.findById(id)
            .orElseThrow(() -> {
                logger.warn("User not found with ID: {}", id);
                return new ResourceNotFoundException(
                        "User with ID " + id + " not found"
                );
            });
}
   @PostMapping
@Operation(summary = "Create user", description = "Creates a new company user")
public User createUser(@Valid @RequestBody User user)
 {
    logger.info("Creating new user with email: {}", user.getEmail());

    User savedUser = userRepository.save(user);

    logger.info("User created successfully with ID: {}",
            savedUser.getUserId());

    return savedUser;
}

    @PutMapping("/{id}")
    @Operation(summary = "Replace user", description = "Replaces all editable fields of a user")
    public ResponseEntity<User> updateUser(
            @PathVariable Integer id,
            @Valid @RequestBody User userDetails) {

        logger.info("Updating user with ID: {}", id);

        return userRepository.findById(id)
                .map(user -> {

                    user.setFullName(userDetails.getFullName());
                    user.setEmail(userDetails.getEmail());
                    user.setPasswordHash(userDetails.getPasswordHash());
                    user.setPhone(userDetails.getPhone());
                    user.setStatus(userDetails.getStatus());
                    user.setRoleId(userDetails.getRoleId());
                    user.setDepartmentId(userDetails.getDepartmentId());
                    user.setCreatedAt(userDetails.getCreatedAt());

                    User updatedUser = userRepository.save(user);
                    logger.info("User with ID {} updated successfully", id);
                    return ResponseEntity.ok(updatedUser);
                })
                .orElseGet(() -> {
                    logger.warn("User not found for update with ID: {}", id);
                    return ResponseEntity.notFound().build();
                });
    }

@PatchMapping("/{id}")
@Operation(summary = "Partially update user", description = "Updates only the supplied user fields")
public ResponseEntity<User> patchUser(
        @PathVariable Integer id,
        @RequestBody User userDetails) {

    logger.info("Patching user with ID: {}", id);

    return userRepository.findById(id)
            .map(user -> {

                if (userDetails.getFullName() != null) {
                    user.setFullName(userDetails.getFullName());
                }

                if (userDetails.getEmail() != null) {
                    user.setEmail(userDetails.getEmail());
                }

                if (userDetails.getPasswordHash() != null) {
                    user.setPasswordHash(userDetails.getPasswordHash());
                }

                if (userDetails.getPhone() != null) {
                    user.setPhone(userDetails.getPhone());
                }

                if (userDetails.getStatus() != null) {
                    user.setStatus(userDetails.getStatus());
                }

                if (userDetails.getRoleId() != null) {
                    user.setRoleId(userDetails.getRoleId());
                }

                if (userDetails.getDepartmentId() != null) {
                    user.setDepartmentId(userDetails.getDepartmentId());
                }

                if (userDetails.getCreatedAt() != null) {
                    user.setCreatedAt(userDetails.getCreatedAt());
                }

                User updatedUser = userRepository.save(user);

                logger.info("User with ID {} updated successfully", id);

                return ResponseEntity.ok(updatedUser);
            })
            .orElseThrow(() -> {
                logger.warn("User not found with ID: {}", id);
                return new ResourceNotFoundException(
                        "User with ID " + id + " not found"
                );
            });
}

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete user", description = "Deletes a user by ID")
    public ResponseEntity<Void> deleteUser(
            @PathVariable Integer id) {

        logger.info("Deleting user with ID: {}", id);

        if (userRepository.existsById(id)) {
            userRepository.deleteById(id);
            logger.info("User with ID {} deleted successfully", id);
            return ResponseEntity.noContent().build();
        }

        logger.warn("User not found for deletion with ID: {}", id);
        return ResponseEntity.notFound().build();
    }
}
