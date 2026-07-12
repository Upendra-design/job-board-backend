package com.jobboard.api.dto.savedjob;

import com.jobboard.api.model.SavedJob;

import java.time.LocalDateTime;

public class SavedJobResponse {

    private Long id;
    private Long jobId;
    private String jobTitle;
    private String companyName;
    private LocalDateTime savedAt;

    public static SavedJobResponse fromEntity(SavedJob savedJob) {
        SavedJobResponse dto = new SavedJobResponse();
        dto.id = savedJob.getId();
        dto.jobId = savedJob.getJob().getId();
        dto.jobTitle = savedJob.getJob().getTitle();
        dto.companyName = savedJob.getJob().getCompany().getName();
        dto.savedAt = savedJob.getSavedAt();
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

    public String getCompanyName() {
        return companyName;
    }

    public LocalDateTime getSavedAt() {
        return savedAt;
    }
}
