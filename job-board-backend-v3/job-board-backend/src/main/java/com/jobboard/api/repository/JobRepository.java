package com.jobboard.api.repository;

import com.jobboard.api.model.ExperienceLevel;
import com.jobboard.api.model.Job;
import com.jobboard.api.model.JobType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface JobRepository extends JpaRepository<Job, Long> {

    /**
     * Combines free-text search with optional structured filters. Any parameter left
     * null is skipped (matches everything for that field). Supports pagination and
     * sorting via the Pageable argument (e.g. ?page=0&size=10&sort=createdAt,desc).
     */
    @Query("""
            SELECT DISTINCT j FROM Job j
            LEFT JOIN j.tags t
            WHERE (:search IS NULL OR
                   LOWER(j.title) LIKE LOWER(CONCAT('%', :search, '%')) OR
                   LOWER(j.company.name) LIKE LOWER(CONCAT('%', :search, '%')) OR
                   LOWER(t) LIKE LOWER(CONCAT('%', :search, '%')))
              AND (:location IS NULL OR LOWER(j.location) LIKE LOWER(CONCAT('%', :location, '%')))
              AND (:minSalary IS NULL OR j.salaryMax >= :minSalary)
              AND (:experienceLevel IS NULL OR j.experienceLevel = :experienceLevel)
              AND (:jobType IS NULL OR j.jobType = :jobType)
              AND (:category IS NULL OR LOWER(j.category) = LOWER(:category))
            """)
    Page<Job> search(
            @Param("search") String search,
            @Param("location") String location,
            @Param("minSalary") Integer minSalary,
            @Param("experienceLevel") ExperienceLevel experienceLevel,
            @Param("jobType") JobType jobType,
            @Param("category") String category,
            Pageable pageable
    );

    List<Job> findByCompanyId(Long companyId);

    List<Job> findByPostedById(Long postedById);
}
