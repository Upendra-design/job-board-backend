package com.jobboard.api.dto.company;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class CompanyRequest {

    @NotBlank(message = "Company name is required")
    private String name;

    private String description;
    private String website;
    private String logoUrl;
    private String location;

    // Which user (employer) owns this company. In a JWT-secured version, you'd pull
    // this from the authenticated principal instead of accepting it in the request body.
    @NotNull(message = "ownerId is required")
    private Long ownerId;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public String getLogoUrl() {
        return logoUrl;
    }

    public void setLogoUrl(String logoUrl) {
        this.logoUrl = logoUrl;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Long getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(Long ownerId) {
        this.ownerId = ownerId;
    }
}
