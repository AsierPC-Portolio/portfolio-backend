package com.asierpc.portfoliobackend.adapter.rest;

import com.asierpc.portfoliobackend.api.model.Project;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.security.test.context.support.WithMockUser;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest(properties = {"spring.profiles.active=test"})
@AutoConfigureMockMvc
class ProjectControllerIT {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @WithMockUser
    void createAndGetProject() throws Exception {
        // Create a project
        Project project = new Project()
                .name("IT Project")
                .description("Integration test")
                .countryCode("ES")
                .link(java.net.URI.create("https://it.com"))
                .startDate(java.time.LocalDate.of(2025, 7, 13))
                .endDate(java.time.LocalDate.of(2025, 12, 31));

        // POST /projects
        ResultActions postResult = mockMvc.perform(post("/projects")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(project)));
        postResult.andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.name").value("IT Project"));

        // Get the created ID
        String response = postResult.andReturn().getResponse().getContentAsString();
        Project created = objectMapper.readValue(response, Project.class);

        mockMvc.perform(get("/projects/" + created.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("IT Project"));
    }
}
