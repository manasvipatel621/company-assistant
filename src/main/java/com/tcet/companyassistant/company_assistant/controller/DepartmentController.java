package com.tcet.companyassistant.company_assistant.controller;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.tcet.companyassistant.company_assistant.entity.Department;
import com.tcet.companyassistant.repository.DepartmentRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/departments")
public class DepartmentController {
    private static final Logger logger =
        LoggerFactory.getLogger(DepartmentController.class);

    private final DepartmentRepository departmentRepository;

    public DepartmentController(DepartmentRepository departmentRepository) {
        this.departmentRepository = departmentRepository;
    }

@GetMapping("/{id}")
public ResponseEntity<Department> getDepartmentById(
        @PathVariable Integer id) {

    logger.info("Fetching department with ID: {}", id);

    return departmentRepository.findById(id)
            .map(department -> {
                logger.info("Department found with ID: {}", id);
                return ResponseEntity.ok(department);
            })
            .orElseGet(() -> {
                logger.warn("Department not found with ID: {}", id);
                return ResponseEntity.notFound().build();
            });
}
@GetMapping
public List<Department> getAllDepartments() {
    logger.info("Fetching all departments");

    List<Department> departments = departmentRepository.findAll();

    logger.info("Found {} departments", departments.size());

    return departments;
}
    @PostMapping
    public Department createDepartment(
            @RequestBody Department department) {

        return departmentRepository.save(department);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Department> updateDepartment(
            @PathVariable Integer id,
            @RequestBody Department departmentDetails) {

        return departmentRepository.findById(id)
                .map(department -> {

                    department.setDepartmentName(
                            departmentDetails.getDepartmentName()
                    );

                    department.setDescription(
                            departmentDetails.getDescription()
                    );

                    department.setHeadUserId(
                            departmentDetails.getHeadUserId()
                    );

                    return ResponseEntity.ok(
                            departmentRepository.save(department)
                    );
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Department> patchDepartment(
            @PathVariable Integer id,
            @RequestBody Department departmentDetails) {

        return departmentRepository.findById(id)
                .map(department -> {

                    if (departmentDetails.getDepartmentName() != null) {
                        department.setDepartmentName(
                                departmentDetails.getDepartmentName()
                        );
                    }

                    if (departmentDetails.getDescription() != null) {
                        department.setDescription(
                                departmentDetails.getDescription()
                        );
                    }

                    if (departmentDetails.getHeadUserId() != null) {
                        department.setHeadUserId(
                                departmentDetails.getHeadUserId()
                        );
                    }

                    return ResponseEntity.ok(
                            departmentRepository.save(department)
                    );
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDepartment(
            @PathVariable Integer id) {

        if (departmentRepository.existsById(id)) {
            departmentRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.notFound().build();
    }
}