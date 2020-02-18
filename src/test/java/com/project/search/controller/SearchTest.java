package com.project.search.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.search.api.Search;
import com.project.search.api.request.SearchRequest;
import com.project.search.searchprovider.SearchService;
import com.project.search.utils.Generators;
import com.project.search.validation.api.SearchRequestValidator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@WebMvcTest(controllers = {Search.class})
public class SearchTest {

  @MockBean SearchService searchService;
  @MockBean SearchRequestValidator searchRequestValidator;
  @Autowired ObjectMapper objectMapper;
  @Autowired private MockMvc mockMvc;

  @BeforeEach
  void setup() throws JsonProcessingException {
    MockitoAnnotations.initMocks(this);
    Mockito.when(searchRequestValidator.validate(ArgumentMatchers.any(SearchRequest.class)))
        .thenReturn(true);
    Mockito.when(searchService.fetch(ArgumentMatchers.any(SearchRequest.class)))
        .thenReturn("hello");
  }

  @Test
  void when() throws Exception {
    mockMvc
        .perform(
            MockMvcRequestBuilders.post("/api/v1/search")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(objectMapper.writeValueAsString(Generators.getSearchRequestForSearch())))
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andExpect(MockMvcResultMatchers.content().string("hello"));
  }

  @Test
  void testfetchIDwithFilter() throws Exception {
    mockMvc
            .perform(
                    MockMvcRequestBuilders.post("/api/v1/search")
                            .contentType(MediaType.APPLICATION_JSON_VALUE)
                            .content(objectMapper.writeValueAsString(Generators.getTicketRequest())))
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(MockMvcResultMatchers.content().string("hello"));
  }
}
