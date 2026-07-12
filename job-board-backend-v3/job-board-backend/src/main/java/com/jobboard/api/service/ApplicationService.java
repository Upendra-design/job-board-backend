package com.jobboard.api.service;

import com.jobboard.api.dto.application.ApplicationRequest;
import com.jobboard.api.dto.application.ApplicationResponse;
import com.jobboard.api.dto.application.UpdateApplicationStatusRequest;
import com.jobboard.api.exception.DuplicateResourceException;
import com.jobboard.api.exception.ResourceNotFoundException;
import com.jobboard.api.model.Application;
import com.jobboard.api.model.Job;
import com.jobboard.api.model.User;
import com.jobboard.api.repository.ApplicationRepository;
import com.jobboard.api.repository.JobRepository;
import com.jobboard.api.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ApplicationService {

    private final ApplicationRepository applicationRepository;
    private final JobRepository jobRepository;
    private final UserRepository userRepository;

    public ApplicationService(ApplicationRepository applicationRepository, JobRepository jobRepository, UserRepository userRepository) {
        this.applicationRepository = applicationRepository;
        this.jobRepository = jobRepository;
        this.userRepository = userRepository;
    }

    public ApplicationResponse apply(Long jobId, ApplicationRequest request) {
        Job job = jobRepository.findById(jobId)
                .orElseThrow(() -> new ResourceNotFoundException("No job found with id " + jobId));
        User applicant = userRepository.findById(request.getApplicantId())
                .orElseThrow(() -> new ResourceNotFoundException("No user found with id " + request.getApplicantId()));

        if (applicationRepository.existsByJobIdAndApplicantId(jobId, request.getApplicantId())) {
            throw new DuplicateResourceException("You've already applied to this job");
        }

        Application application = new Application();
        application.setJob(job);
        application.setApplicant(applicant);
        application.setNote(request.getNote());

        return ApplicationResponse.fromEntity(applicationRepository.save(application));
    }

    // Used by employers to view everyone who applied to one of their job postings
    public List<ApplicationResponse> getApplicantsForJob(Long jobId) {
        if (!jobRepository.existsById(jobId)) {
            throw new ResourceNotFoundException("No job found with id " + jobId);
        }
        return applicationRepository.findByJobId(jobId).stream()
                .map(ApplicationResponse::fromEntity)
                .toList();
    }

    // Used by job seekers to view jobs they've applied to
    public List<ApplicationResponse> getApplicationsForUser(Long userId) {
        return applicationRepository.findByApplicantId(userId).stream()
                .map(ApplicationResponse::fromEntity)
                .toList();
    }

    public ApplicationResponse updateStatus(Long applicationId, UpdateApplicationStatusRequest request) {
        Application application = applicationRepository.findById(applicationId)
                .orElseThrow(() -> new ResourceNotFoundException("No application found with id " + applicationId));
        application.setStatus(request.getStatus());
        return ApplicationResponse.fromEntity(applicationRepository.save(application));
    }
}
