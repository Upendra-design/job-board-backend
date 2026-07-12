package com.jobboard.api.dto.company;

import com.jobboard.api.model.Company;

import java.time.LocalDateTime;

public class CompanyResponse {

    private Long id;
    private String name;
    private String description;
    private String website;
    private String logoUrl;
    private String location;
    private Long ownerId;
    private String ownerName;
    private LocalDateTime createdAt;

    public static CompanyResponse fromEntity(Company company) {
        CompanyResponse dto = new CompanyResponse();
        dto.id = company.getId();
        dto.name = company.getName();
        dto.description = company.getDescription();
        dto.website = company.getWebsite();
        dto.logoUrl = company.getLogoUrl();
        dto.location = company.getLocation();
        dto.ownerId = company.getOwner().getId();
        dto.ownerName = company.getOwner().getFullName();
        dto.createdAt = company.getCreatedAt();
        return dto;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getWebsite() {
        return website;
    }

    public String getLogoUrl() {
        return logoUrl;
    }

    public String getLocation() {
        return location;
    }

    public Long getOwnerId() {
        return ownerId;
    }

    public String getOwnerName() {
        return ownerName;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
}
