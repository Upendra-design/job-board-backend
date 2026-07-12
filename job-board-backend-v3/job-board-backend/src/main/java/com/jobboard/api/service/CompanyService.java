package com.jobboard.api.service;

import com.jobboard.api.dto.company.CompanyRequest;
import com.jobboard.api.dto.company.CompanyResponse;
import com.jobboard.api.exception.ResourceNotFoundException;
import com.jobboard.api.model.Company;
import com.jobboard.api.model.User;
import com.jobboard.api.repository.CompanyRepository;
import com.jobboard.api.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CompanyService {

    private final CompanyRepository companyRepository;
    private final UserRepository userRepository;

    public CompanyService(CompanyRepository companyRepository,
                          UserRepository userRepository) {
        this.companyRepository = companyRepository;
        this.userRepository = userRepository;
    }

    @Transactional(readOnly = true)
    public List<CompanyResponse> getAll() {

        return companyRepository.findAll()
                .stream()
                .map(company -> {

                    // Initialize lazy owner
                    company.getOwner().getId();
                    company.getOwner().getFullName();

                    return CompanyResponse.fromEntity(company);
                })
                .toList();
    }

    @Transactional(readOnly = true)
    public CompanyResponse getById(Long id) {

        Company company = findEntity(id);

        // Initialize lazy owner
        company.getOwner().getId();
        company.getOwner().getFullName();

        return CompanyResponse.fromEntity(company);
    }

    @Transactional
    public CompanyResponse create(CompanyRequest request) {

        User owner = userRepository.findById(request.getOwnerId())
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "No user found with id " + request.getOwnerId()));

        Company company = new Company();

        applyRequest(company, request);
        company.setOwner(owner);

        Company savedCompany = companyRepository.save(company);

        savedCompany.getOwner().getId();
        savedCompany.getOwner().getFullName();

        return CompanyResponse.fromEntity(savedCompany);
    }

    @Transactional
    public CompanyResponse update(Long id, CompanyRequest request) {

        Company company = findEntity(id);

        applyRequest(company, request);

        Company updatedCompany = companyRepository.save(company);

        updatedCompany.getOwner().getId();
        updatedCompany.getOwner().getFullName();

        return CompanyResponse.fromEntity(updatedCompany);
    }

    @Transactional
    public void delete(Long id) {

        Company company = findEntity(id);

        companyRepository.delete(company);
    }

    private Company findEntity(Long id) {

        return companyRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "No company found with id " + id));
    }

    private void applyRequest(Company company, CompanyRequest request) {

        company.setName(request.getName());
        company.setDescription(request.getDescription());
        company.setWebsite(request.getWebsite());
        company.setLogoUrl(request.getLogoUrl());
        company.setLocation(request.getLocation());
    }
}