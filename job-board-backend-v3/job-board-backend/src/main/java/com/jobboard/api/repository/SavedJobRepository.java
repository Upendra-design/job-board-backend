package com.jobboard.api.repository;

import com.jobboard.api.model.SavedJob;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.EntityGraph;

import java.util.List;
import java.util.Optional;

public interface SavedJobRepository extends JpaRepository<SavedJob, Long> {

    @EntityGraph(attributePaths = {"job","job.company"})
    List<SavedJob> findByUserId(Long userId);

    Optional<SavedJob> findByUserIdAndJobId(Long userId, Long jobId);

    boolean existsByUserIdAndJobId(Long userId, Long jobId);

    void deleteByUserIdAndJobId(Long userId, Long jobId);
}