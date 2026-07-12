package com.jobboard.api.dto.application;

import jakarta.validation.constraints.NotNull;

public class ApplicationRequest {

    // In a JWT-secured version, derive this from the authenticated principal instead
    // of accepting it in the request body.
    @NotNull(message = "applicantId is required")
    private Long applicantId;

    private String note;

    public Long getApplicantId() {
        return applicantId;
    }

    public void setApplicantId(Long applicantId) {
        this.applicantId = applicantId;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }
}
