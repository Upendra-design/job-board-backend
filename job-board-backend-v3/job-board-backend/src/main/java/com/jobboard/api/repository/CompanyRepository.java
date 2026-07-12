package com.jobboard.api.repository;

import com.jobboard.api.model.Company;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CompanyRepository extends JpaRepository<Company, Long> {
    List<Company> findByOwnerId(Long ownerId);
}
