package com.jobboard.api;

import com.jobboard.api.repository.CompanyRepository;
import com.jobboard.api.repository.JobRepository;
import com.jobboard.api.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class JobBoardApiApplicationTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private JobRepository jobRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CompanyRepository companyRepository;

    @Test
    void contextLoads() {
        // Verifies the Spring context wires up correctly (JPA, controllers, CORS config, etc.)
    }

    @Test
    void seedDataIsLoadedOnStartup() {
        assertThat(userRepository.count()).isGreaterThan(0);
        assertThat(companyRepository.count()).isGreaterThan(0);
        assertThat(jobRepository.count()).isGreaterThan(0);
    }

    @Test
    void jobsEndpointReturnsOk() throws Exception {
        mockMvc.perform(get("/api/jobs"))
                .andExpect(status().isOk());
    }

    @Test
    void jobsEndpointSupportsFilteringAndPagination() throws Exception {
        mockMvc.perform(get("/api/jobs")
                        .param("jobType", "FULL_TIME")
                        .param("page", "0")
                        .param("size", "5"))
                .andExpect(status().isOk());
    }

    @Test
    void companiesEndpointReturnsOk() throws Exception {
        mockMvc.perform(get("/api/companies"))
                .andExpect(status().isOk());
    }

    @Test
    void healthEndpointReturnsOk() throws Exception {
        mockMvc.perform(get("/api/health"))
                .andExpect(status().isOk());
    }

    @Test
    void unknownJobIdReturns404() throws Exception {
        mockMvc.perform(get("/api/jobs/999999"))
                .andExpect(status().isNotFound());
    }

    @Test
    void unknownUserIdReturns404() throws Exception {
        mockMvc.perform(get("/api/users/999999"))
                .andExpect(status().isNotFound());
    }
}
