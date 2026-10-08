package com.tcet.companyassistant.company_assistant.controller;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import jakarta.validation.Valid;
import com.tcet.companyassistant.company_assistant.entity.Role;
import com.tcet.companyassistant.repository.RoleRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.tags.Tag;

import java.util.List;

@RestController
@RequestMapping("/api/roles")
@Tag(name = "Roles", description = "Manage user roles")
public class RoleController {
    private static final Logger logger =
        LoggerFactory.getLogger(RoleController.class);

    private final RoleRepository roleRepository;

    public RoleController(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

  @GetMapping
public List<Role> getAllRoles() {
    logger.info("Fetching all roles");

    List<Role> roles = roleRepository.findAll();

    logger.info("Found {} roles", roles.size());

    return roles;
}
@GetMapping("/{id}")
public ResponseEntity<Role> getRoleById(
        @PathVariable Integer id) {

    logger.info("Fetching role with ID: {}", id);

    return roleRepository.findById(id)
            .map(role -> {
                logger.info("Role found with ID: {}", id);
                return ResponseEntity.ok(role);
            })
            .orElseGet(() -> {
                logger.warn("Role not found with ID: {}", id);
                return ResponseEntity.notFound().build();
            });
}
    @PostMapping
    public Role createRole(@Valid @RequestBody Role role) {
        return roleRepository.save(role);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Role> updateRole(
            @PathVariable Integer id,
            @Valid @RequestBody Role roleDetails) {

        return roleRepository.findById(id)
                .map(role -> {

                    role.setRoleName(roleDetails.getRoleName());
                    role.setDescription(roleDetails.getDescription());

                    return ResponseEntity.ok(
                            roleRepository.save(role)
                    );
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Role> patchRole(
            @PathVariable Integer id,
            @RequestBody Role roleDetails) {

        return roleRepository.findById(id)
                .map(role -> {

                    if (roleDetails.getRoleName() != null) {
                        role.setRoleName(roleDetails.getRoleName());
                    }

                    if (roleDetails.getDescription() != null) {
                        role.setDescription(roleDetails.getDescription());
                    }

                    return ResponseEntity.ok(
                            roleRepository.save(role)
                    );
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRole(
            @PathVariable Integer id) {

        if (roleRepository.existsById(id)) {
            roleRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.notFound().build();
    }
}
