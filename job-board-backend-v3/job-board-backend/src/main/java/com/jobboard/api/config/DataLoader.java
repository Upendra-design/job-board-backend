package com.jobboard.api.config;

import com.jobboard.api.model.*;
import com.jobboard.api.repository.CompanyRepository;
import com.jobboard.api.repository.JobRepository;
import com.jobboard.api.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class DataLoader implements CommandLineRunner {

    private final UserRepository userRepository;
    private final CompanyRepository companyRepository;
    private final JobRepository jobRepository;

    public DataLoader(UserRepository userRepository, CompanyRepository companyRepository, JobRepository jobRepository) {
        this.userRepository = userRepository;
        this.companyRepository = companyRepository;
        this.jobRepository = jobRepository;
    }

    @Override
    public void run(String... args) {
        // Don't reseed on every restart (matters once ddl-auto=update is used against a real MySQL DB)
        if (userRepository.count() > 0) {
            return;
        }

        // NOTE: plaintext passwords here purely as seed data for this scaffold.
        // Once you add Spring Security, register real users through the API so
        // passwords get hashed by your encoder instead of relying on this seed.
        User employer1 = saveUser("Aisha Rao", "aisha@northwindlabs.com", "password123", Role.EMPLOYER);
        User employer2 = saveUser("Marco Silva", "marco@ferroussystems.com", "password123", Role.EMPLOYER);
        saveUser("Priya Nair", "priya@example.com", "password123", Role.JOB_SEEKER);
        saveUser("Admin User", "admin@lantern.dev", "password123", Role.ADMIN);

        Company northwind = saveCompany("Northwind Labs", "Tools for independent bookstores.",
                "https://northwindlabs.example.com", "Hyderabad, IN", employer1);
        Company ferrous = saveCompany("Ferrous Systems", "Ledger infrastructure for fintech.",
                "https://ferroussystems.example.com", "Bengaluru, IN", employer2);

        jobRepository.saveAll(List.of(
                buildJob(
                        "Frontend Engineer", northwind, employer1, "Hyderabad, IN",
                        1200000, 1800000, ExperienceLevel.MID, JobType.FULL_TIME, "Engineering",
                        List.of("React", "TypeScript", "Tailwind"),
                        "Northwind Labs is building tools for independent bookstores. We need someone who cares about fast, accessible interfaces."
                ),
                buildJob(
                        "Backend Engineer", ferrous, employer2, "Bengaluru, IN",
                        1500000, 2200000, ExperienceLevel.SENIOR, JobType.FULL_TIME, "Engineering",
                        List.of("Java", "Spring Boot", "MySQL"),
                        "Ferrous Systems runs the ledger infrastructure behind a growing fintech platform."
                ),
                buildJob(
                        "Product Designer", northwind, employer1, "Remote",
                        900000, 1400000, ExperienceLevel.MID, JobType.CONTRACT, "Design",
                        List.of("Figma", "Design Systems"),
                        "We're looking for a designer who enjoys turning messy workflows into clear interfaces."
                ),
                buildJob(
                        "DevOps Engineer", ferrous, employer2, "Hyderabad, IN",
                        1800000, 2600000, ExperienceLevel.SENIOR, JobType.FULL_TIME, "Engineering",
                        List.of("Kubernetes", "CI/CD", "Terraform"),
                        "Build and maintain the CI/CD pipelines and platform our customers rely on daily."
                )
        ));
    }

    private User saveUser(String fullName, String email, String password, Role role) {
        User user = new User();
        user.setFullName(fullName);
        user.setEmail(email);
        user.setPassword(password);
        user.setRole(role);
        return userRepository.save(user);
    }

    private Company saveCompany(String name, String description, String website, String location, User owner) {
        Company company = new Company();
        company.setName(name);
        company.setDescription(description);
        company.setWebsite(website);
        company.setLocation(location);
        company.setOwner(owner);
        return companyRepository.save(company);
    }

    private Job buildJob(
            String title, Company company, User postedBy, String location,
            Integer salaryMin, Integer salaryMax, ExperienceLevel experienceLevel,
            JobType jobType, String category, List<String> tags, String description
    ) {
        Job job = new Job();
        job.setTitle(title);
        job.setCompany(company);
        job.setPostedBy(postedBy);
        job.setLocation(location);
        job.setSalaryMin(salaryMin);
        job.setSalaryMax(salaryMax);
        job.setExperienceLevel(experienceLevel);
        job.setJobType(jobType);
        job.setCategory(category);
        job.setTags(tags);
        job.setDescription(description);
        return job;
    }
}
