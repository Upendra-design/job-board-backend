package com.jobboard.api.controller;

import com.jobboard.api.dto.savedjob.SavedJobResponse;
import com.jobboard.api.service.SavedJobService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class SavedJobController {

    private final SavedJobService savedJobService;

    public SavedJobController(SavedJobService savedJobService) {
        this.savedJobService = savedJobService;
    }

    // POST /api/users/{userId}/saved-jobs/{jobId} — job seeker saves a job
    @PostMapping("/api/users/{userId}/saved-jobs/{jobId}")
    @ResponseStatus(HttpStatus.CREATED)
    public SavedJobResponse save(@PathVariable Long userId, @PathVariable Long jobId) {
        return savedJobService.save(userId, jobId);
    }

    // DELETE /api/users/{userId}/saved-jobs/{jobId} — job seeker removes a saved job
    @DeleteMapping("/api/users/{userId}/saved-jobs/{jobId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void remove(@PathVariable Long userId, @PathVariable Long jobId) {
        savedJobService.remove(userId, jobId);
    }

    // GET /api/users/{userId}/saved-jobs — job seeker views their saved jobs
    @GetMapping("/api/users/{userId}/saved-jobs")
    public List<SavedJobResponse> getSavedJobs(@PathVariable Long userId) {
        return savedJobService.getSavedJobsForUser(userId);
    }
}
