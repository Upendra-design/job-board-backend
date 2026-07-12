package com.jobboard.api.dto.application;

import com.jobboard.api.model.ApplicationStatus;
import jakarta.validation.constraints.NotNull;

public class UpdateApplicationStatusRequest {

    @NotNull(message = "status is required")
    private ApplicationStatus status;

    public ApplicationStatus getStatus() {
        return status;
    }

    public void setStatus(ApplicationStatus status) {
        this.status = status;
    }
}
