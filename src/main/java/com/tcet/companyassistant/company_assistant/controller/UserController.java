package com.tcet.companyassistant.company_assistant.controller;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.tcet.companyassistant.company_assistant.entity.User;
import com.tcet.companyassistant.company_assistant.exception.ResourceNotFoundException;
import com.tcet.companyassistant.repository.UserRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {
    private static final Logger logger =
        LoggerFactory.getLogger(UserController.class);

    private final UserRepository userRepository;

    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping
public List<User> getAllUsers() {
    logger.info("Fetching all users");

    List<User> users = userRepository.findAll();

    logger.info("Found {} users", users.size());

    return users;
}


    @GetMapping("/{id}")
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
public User createUser(@RequestBody User user)
 {
    logger.info("Creating new user with email: {}", user.getEmail());

    User savedUser = userRepository.save(user);

    logger.info("User created successfully with ID: {}",
            savedUser.getUserId());

    return savedUser;
}

    @PutMapping("/{id}")
    public ResponseEntity<User> updateUser(
            @PathVariable Integer id,
            @RequestBody User userDetails) {

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

                    return ResponseEntity.ok(
                            userRepository.save(user)
                    );
                })
                .orElse(ResponseEntity.notFound().build());
    }

@PatchMapping("/{id}")
public ResponseEntity<User> patchUser(
        @PathVariable Integer id,
        @Valid @RequestBody User userDetails) {

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
    public ResponseEntity<Void> deleteUser(
            @PathVariable Integer id) {

        if (userRepository.existsById(id)) {
            userRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.notFound().build();
    }
}