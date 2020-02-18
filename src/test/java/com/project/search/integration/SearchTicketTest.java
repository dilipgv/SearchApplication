package com.project.search.integration;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.search.searchprovider.SearchService;
import com.project.search.utils.Generators;
import com.project.search.validation.api.SearchRequestValidator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@SpringBootTest
@AutoConfigureMockMvc
public class SearchTicketTest {

  @Autowired ObjectMapper objectMapper;
  @Autowired MockMvc mockMvc;

  @BeforeEach
  void setup() throws JsonProcessingException {}

  @Test
  void when() throws Exception {
    mockMvc
        .perform(
            MockMvcRequestBuilders.post("/api/v1/search")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(objectMapper.writeValueAsString(Generators.getTicketRequest())))
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andExpect(MockMvcResultMatchers.content().string(Generators.getTicketResponse()));
  }
}
