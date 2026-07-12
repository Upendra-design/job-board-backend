package com.jobboard.api.controller;

import com.jobboard.api.dto.job.JobRequest;
import com.jobboard.api.dto.job.JobResponse;
import com.jobboard.api.model.ExperienceLevel;
import com.jobboard.api.model.JobType;
import com.jobboard.api.service.JobService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/jobs")
public class JobController {

    private final JobService jobService;

    public JobController(JobService jobService) {
        this.jobService = jobService;
    }

    /**
     * GET /api/jobs
     * GET /api/jobs?search=react&location=Hyderabad&minSalary=1200000
     * GET /api/jobs?experienceLevel=SENIOR&jobType=FULL_TIME&category=Engineering
     * GET /api/jobs?page=0&size=10&sortBy=createdAt&sortDir=desc
     *
     * All filters are optional and combine with AND logic. Response is a Spring Page,
     * which includes content, totalElements, totalPages, etc. for frontend pagination.
     */
    @GetMapping
    public Page<JobResponse> getJobs(
            @RequestParam(required = false) String search,
            @RequestParam(required = false) String location,
            @RequestParam(required = false) Integer minSalary,
            @RequestParam(required = false) ExperienceLevel experienceLevel,
            @RequestParam(required = false) JobType jobType,
            @RequestParam(required = false) String category,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "createdAt") String sortBy,
            @RequestParam(defaultValue = "desc") String sortDir
    ) {
        Sort sort = sortDir.equalsIgnoreCase("asc") ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
        Pageable pageable = PageRequest.of(page, size, sort);
        return jobService.search(search, location, minSalary, experienceLevel, jobType, category, pageable);
    }

    // GET /api/jobs/{id}
    @GetMapping("/{id}")
    public JobResponse getById(@PathVariable Long id) {
        return jobService.getById(id);
    }

    // POST /api/jobs — employer posts a new job
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public JobResponse create(@Valid @RequestBody JobRequest request) {
        return jobService.create(request);
    }

    // PUT /api/jobs/{id} — employer edits a job
    @PutMapping("/{id}")
    public JobResponse update(@PathVariable Long id, @Valid @RequestBody JobRequest request) {
        return jobService.update(id, request);
    }

    // DELETE /api/jobs/{id}
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        jobService.delete(id);
    }
}
