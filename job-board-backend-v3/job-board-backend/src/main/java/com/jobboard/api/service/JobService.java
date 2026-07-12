package com.jobboard.api.service;

import com.jobboard.api.dto.job.JobRequest;
import com.jobboard.api.dto.job.JobResponse;
import com.jobboard.api.exception.ResourceNotFoundException;
import com.jobboard.api.model.Company;
import com.jobboard.api.model.ExperienceLevel;
import com.jobboard.api.model.Job;
import com.jobboard.api.model.JobType;
import com.jobboard.api.model.User;
import com.jobboard.api.repository.CompanyRepository;
import com.jobboard.api.repository.JobRepository;
import com.jobboard.api.repository.UserRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class JobService {

    private final JobRepository jobRepository;
    private final CompanyRepository companyRepository;
    private final UserRepository userRepository;

    public JobService(JobRepository jobRepository, CompanyRepository companyRepository, UserRepository userRepository) {
        this.jobRepository = jobRepository;
        this.companyRepository = companyRepository;
        this.userRepository = userRepository;
    }

    public Page<JobResponse> search(
            String search, String location, Integer minSalary,
            ExperienceLevel experienceLevel, JobType jobType, String category,
            Pageable pageable
    ) {
        return jobRepository
                .search(normalize(search), normalize(location), minSalary, experienceLevel, jobType, normalize(category), pageable)
                .map(JobResponse::fromEntity);
    }

    public JobResponse getById(Long id) {
        return JobResponse.fromEntity(findEntity(id));
    }

    public JobResponse create(JobRequest request) {
        Company company = companyRepository.findById(request.getCompanyId())
                .orElseThrow(() -> new ResourceNotFoundException("No company found with id " + request.getCompanyId()));
        User postedBy = userRepository.findById(request.getPostedById())
                .orElseThrow(() -> new ResourceNotFoundException("No user found with id " + request.getPostedById()));

        Job job = new Job();
        applyRequest(job, request);
        job.setCompany(company);
        job.setPostedBy(postedBy);

        return JobResponse.fromEntity(jobRepository.save(job));
    }

    public JobResponse update(Long id, JobRequest request) {
        Job job = findEntity(id);
        applyRequest(job, request);

        if (!job.getCompany().getId().equals(request.getCompanyId())) {
            Company company = companyRepository.findById(request.getCompanyId())
                    .orElseThrow(() -> new ResourceNotFoundException("No company found with id " + request.getCompanyId()));
            job.setCompany(company);
        }

        return JobResponse.fromEntity(jobRepository.save(job));
    }

    public void delete(Long id) {
        Job job = findEntity(id);
        jobRepository.delete(job);
    }

    private Job findEntity(Long id) {
        return jobRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No job found with id " + id));
    }

    private void applyRequest(Job job, JobRequest request) {
        job.setTitle(request.getTitle());
        job.setDescription(request.getDescription());
        job.setLocation(request.getLocation());
        job.setSalaryMin(request.getSalaryMin());
        job.setSalaryMax(request.getSalaryMax());
        job.setExperienceLevel(request.getExperienceLevel());
        job.setJobType(request.getJobType());
        job.setCategory(request.getCategory());
        job.setTags(request.getTags());
    }

    private String normalize(String value) {
        if (value == null || value.isBlank()) {
            return null;
        }
        return value.trim();
    }
}
