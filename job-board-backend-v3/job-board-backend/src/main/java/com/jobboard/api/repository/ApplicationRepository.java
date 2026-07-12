package com.jobboard.api.repository;

import com.jobboard.api.model.Application;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ApplicationRepository extends JpaRepository<Application, Long> {
    List<Application> findByJobId(Long jobId);
    List<Application> findByApplicantId(Long applicantId);
    Optional<Application> findByJobIdAndApplicantId(Long jobId, Long applicantId);
    boolean existsByJobIdAndApplicantId(Long jobId, Long applicantId);
}
