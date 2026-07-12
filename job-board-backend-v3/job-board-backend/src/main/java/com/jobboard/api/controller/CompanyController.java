package com.jobboard.api.controller;

import com.jobboard.api.dto.company.CompanyRequest;
import com.jobboard.api.dto.company.CompanyResponse;
import com.jobboard.api.service.CompanyService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/companies")
public class CompanyController {

    private final CompanyService companyService;

    public CompanyController(CompanyService companyService) {
        this.companyService = companyService;
    }

    // GET /api/companies
    @GetMapping
    public List<CompanyResponse> getAll() {
        return companyService.getAll();
    }

    // GET /api/companies/{id}
    @GetMapping("/{id}")
    public CompanyResponse getById(@PathVariable Long id) {
        return companyService.getById(id);
    }

    // POST /api/companies
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CompanyResponse create(@Valid @RequestBody CompanyRequest request) {
        return companyService.create(request);
    }

    // PUT /api/companies/{id}
    @PutMapping("/{id}")
    public CompanyResponse update(@PathVariable Long id, @Valid @RequestBody CompanyRequest request) {
        return companyService.update(id, request);
    }

    // DELETE /api/companies/{id}
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        companyService.delete(id);
    }
}
