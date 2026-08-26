package com.tcet.companyassistant.company_assistant.controller;

import com.tcet.companyassistant.company_assistant.entity.Task;
import com.tcet.companyassistant.repository.TaskRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.List;

@RestController
@RequestMapping("/api/tasks")
public class TaskController {
private static final Logger logger =
        LoggerFactory.getLogger(TaskController.class);
    private final TaskRepository taskRepository;

    public TaskController(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

   @GetMapping
public List<Task> getAllTasks() {
    logger.info("Fetching all tasks");

    List<Task> tasks = taskRepository.findAll();

    logger.info("Found {} tasks", tasks.size());

    return tasks;
}
   @GetMapping("/{id}")
public ResponseEntity<Task> getTaskById(
        @PathVariable Integer id) {

    logger.info("Fetching task with ID: {}", id);

    return taskRepository.findById(id)
            .map(task -> {
                logger.info("Task found with ID: {}", id);
                return ResponseEntity.ok(task);
            })
            .orElseGet(() -> {
                logger.warn("Task not found with ID: {}", id);
                return ResponseEntity.notFound().build();
            });
}

    @PostMapping
    public Task createTask(@RequestBody Task task) {
        return taskRepository.save(task);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Task> updateTask(
            @PathVariable Integer id,
            @RequestBody Task taskDetails) {

        return taskRepository.findById(id)
                .map(task -> {

                    task.setTitle(taskDetails.getTitle());
                    task.setStatus(taskDetails.getStatus());
                    task.setPriority(taskDetails.getPriority());
                    task.setDueDate(taskDetails.getDueDate());
                    task.setCreatedBy(taskDetails.getCreatedBy());
                    task.setCreatedAt(taskDetails.getCreatedAt());

                    return ResponseEntity.ok(
                            taskRepository.save(task)
                    );
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Task> patchTask(
            @PathVariable Integer id,
            @RequestBody Task taskDetails) {

        return taskRepository.findById(id)
                .map(task -> {

                    if (taskDetails.getTitle() != null) {
                        task.setTitle(taskDetails.getTitle());
                    }

                    if (taskDetails.getStatus() != null) {
                        task.setStatus(taskDetails.getStatus());
                    }

                    if (taskDetails.getPriority() != null) {
                        task.setPriority(taskDetails.getPriority());
                    }

                    if (taskDetails.getDueDate() != null) {
                        task.setDueDate(taskDetails.getDueDate());
                    }

                    if (taskDetails.getCreatedBy() != null) {
                        task.setCreatedBy(taskDetails.getCreatedBy());
                    }

                    if (taskDetails.getCreatedAt() != null) {
                        task.setCreatedAt(taskDetails.getCreatedAt());
                    }

                    return ResponseEntity.ok(
                            taskRepository.save(task)
                    );
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTask(
            @PathVariable Integer id) {

        if (taskRepository.existsById(id)) {
            taskRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.notFound().build();
    }
}