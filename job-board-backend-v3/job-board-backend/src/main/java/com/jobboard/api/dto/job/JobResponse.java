package com.jobboard.api.dto.job;

import com.jobboard.api.model.ExperienceLevel;
import com.jobboard.api.model.Job;
import com.jobboard.api.model.JobType;

import java.time.LocalDateTime;
import java.util.List;

public class JobResponse {

    private Long id;
    private String title;
    private String description;
    private String location;
    private Integer salaryMin;
    private Integer salaryMax;
    private ExperienceLevel experienceLevel;
    private JobType jobType;
    private String category;
    private List<String> tags;
    private Long companyId;
    private String companyName;
    private String companyLogoUrl;
    private Long postedById;
    private String postedByName;
    private LocalDateTime createdAt;

    public static JobResponse fromEntity(Job job) {
        JobResponse dto = new JobResponse();
        dto.id = job.getId();
        dto.title = job.getTitle();
        dto.description = job.getDescription();
        dto.location = job.getLocation();
        dto.salaryMin = job.getSalaryMin();
        dto.salaryMax = job.getSalaryMax();
        dto.experienceLevel = job.getExperienceLevel();
        dto.jobType = job.getJobType();
        dto.category = job.getCategory();
        dto.tags = job.getTags();
        dto.companyId = job.getCompany().getId();
        dto.companyName = job.getCompany().getName();
        dto.companyLogoUrl = job.getCompany().getLogoUrl();
        dto.postedById = job.getPostedBy().getId();
        dto.postedByName = job.getPostedBy().getFullName();
        dto.createdAt = job.getCreatedAt();
        return dto;
    }

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getLocation() {
        return location;
    }

    public Integer getSalaryMin() {
        return salaryMin;
    }

    public Integer getSalaryMax() {
        return salaryMax;
    }

    public ExperienceLevel getExperienceLevel() {
        return experienceLevel;
    }

    public JobType getJobType() {
        return jobType;
    }

    public String getCategory() {
        return category;
    }

    public List<String> getTags() {
        return tags;
    }

    public Long getCompanyId() {
        return companyId;
    }

    public String getCompanyName() {
        return companyName;
    }

    public String getCompanyLogoUrl() {
        return companyLogoUrl;
    }

    public Long getPostedById() {
        return postedById;
    }

    public String getPostedByName() {
        return postedByName;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
}
