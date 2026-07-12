package com.jobboard.api.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jobboard.api.dto.savedjob.SavedJobResponse;
import com.jobboard.api.exception.DuplicateResourceException;
import com.jobboard.api.exception.ResourceNotFoundException;
import com.jobboard.api.model.Job;
import com.jobboard.api.model.SavedJob;
import com.jobboard.api.model.User;
import com.jobboard.api.repository.JobRepository;
import com.jobboard.api.repository.SavedJobRepository;
import com.jobboard.api.repository.UserRepository;

@Service
public class SavedJobService {

    private final SavedJobRepository savedJobRepository;
    private final UserRepository userRepository;
    private final JobRepository jobRepository;

    public SavedJobService(SavedJobRepository savedJobRepository, UserRepository userRepository, JobRepository jobRepository) {
        this.savedJobRepository = savedJobRepository;
        this.userRepository = userRepository;
        this.jobRepository = jobRepository;
    }

    public SavedJobResponse save(Long userId, Long jobId) {
        if (savedJobRepository.existsByUserIdAndJobId(userId, jobId)) {
            throw new DuplicateResourceException("This job is already saved");
        }

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("No user found with id " + userId));
        Job job = jobRepository.findById(jobId)
                .orElseThrow(() -> new ResourceNotFoundException("No job found with id " + jobId));

        SavedJob savedJob = new SavedJob();
        savedJob.setUser(user);
        savedJob.setJob(job);

        return SavedJobResponse.fromEntity(savedJobRepository.save(savedJob));
    }

    @Transactional
    public void remove(Long userId, Long jobId) {
        if (!savedJobRepository.existsByUserIdAndJobId(userId, jobId)) {
            throw new ResourceNotFoundException("This job isn't saved for this user");
        }
        savedJobRepository.deleteByUserIdAndJobId(userId, jobId);
    }

    @Transactional(readOnly = true)
    public List<SavedJobResponse> getSavedJobsForUser(Long userId) {

        return savedJobRepository.findByUserId(userId)
                .stream()
                .map(SavedJobResponse::fromEntity)
                .toList();
    }
}
