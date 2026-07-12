package com.jobboard.api.dto.application;

import com.jobboard.api.model.Application;
import com.jobboard.api.model.ApplicationStatus;

import java.time.LocalDateTime;

public class ApplicationResponse {

    private Long id;
    private Long jobId;
    private String jobTitle;
    private Long applicantId;
    private String applicantName;
    private String note;
    private ApplicationStatus status;
    private LocalDateTime appliedAt;

    public static ApplicationResponse fromEntity(Application application) {
        ApplicationResponse dto = new ApplicationResponse();
        dto.id = application.getId();
        dto.jobId = application.getJob().getId();
        dto.jobTitle = application.getJob().getTitle();
        dto.applicantId = application.getApplicant().getId();
        dto.applicantName = application.getApplicant().getFullName();
        dto.note = application.getNote();
        dto.status = application.getStatus();
        dto.appliedAt = application.getAppliedAt();
        return dto;
    }

    public Long getId() {
        return id;
    }

    public Long getJobId() {
        return jobId;
    }

    public String getJobTitle() {
        return jobTitle;
    }

    public Long getApplicantId() {
        return applicantId;
    }

    public String getApplicantName() {
        return applicantName;
    }

    public String getNote() {
        return note;
    }

    public ApplicationStatus getStatus() {
        return status;
    }

    public LocalDateTime getAppliedAt() {
        return appliedAt;
    }
}
