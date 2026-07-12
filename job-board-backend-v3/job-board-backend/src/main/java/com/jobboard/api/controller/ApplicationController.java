package com.jobboard.api.controller;

import com.jobboard.api.dto.application.ApplicationRequest;
import com.jobboard.api.dto.application.ApplicationResponse;
import com.jobboard.api.dto.application.UpdateApplicationStatusRequest;
import com.jobboard.api.service.ApplicationService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ApplicationController {

    private final ApplicationService applicationService;

    public ApplicationController(ApplicationService applicationService) {
        this.applicationService = applicationService;
    }

    // POST /api/jobs/{jobId}/apply — job seeker applies to a job
    @PostMapping("/api/jobs/{jobId}/apply")
    @ResponseStatus(HttpStatus.CREATED)
    public ApplicationResponse apply(@PathVariable Long jobId, @Valid @RequestBody ApplicationRequest request) {
        return applicationService.apply(jobId, request);
    }

    // GET /api/jobs/{jobId}/applicants — employer views everyone who applied
    @GetMapping("/api/jobs/{jobId}/applicants")
    public List<ApplicationResponse> getApplicants(@PathVariable Long jobId) {
        return applicationService.getApplicantsForJob(jobId);
    }

    // GET /api/users/{userId}/applications — job seeker views their applied jobs
    @GetMapping("/api/users/{userId}/applications")
    public List<ApplicationResponse> getApplicationsForUser(@PathVariable Long userId) {
        return applicationService.getApplicationsForUser(userId);
    }

    // PATCH /api/applications/{applicationId}/status — employer updates an applicant's status
    @PatchMapping("/api/applications/{applicationId}/status")
    public ApplicationResponse updateStatus(
            @PathVariable Long applicationId,
            @Valid @RequestBody UpdateApplicationStatusRequest request
    ) {
        return applicationService.updateStatus(applicationId, request);
    }
}
