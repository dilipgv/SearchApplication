package com.project.search.api;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.search.api.request.SearchRequest;
import com.project.search.errorhandling.exceptions.ApiFieldException;
import com.project.search.searchprovider.SearchService;
import com.project.search.validation.api.SearchRequestValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1")
public class Search {

  @Autowired SearchService searchService;
  @Autowired SearchRequestValidator searchRequestValidator;

  private static Logger logger = LoggerFactory.getLogger(Search.class);

  @PostMapping("/search")
  public String search(@RequestBody SearchRequest request)
      throws JsonProcessingException, ApiFieldException {

    logger.info("New request recieved");
    logger.debug("Request payload:" + new ObjectMapper().writeValueAsString(request));

    if (searchRequestValidator.validate(request)) return searchService.fetch(request);
    return "";
  }

  @GetMapping("/search")
  public String searchGet() throws ApiFieldException {
    throw new ApiFieldException("Incorrect API Error");
  }
}
