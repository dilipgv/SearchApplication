package com.project.search.searchprovider;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.project.search.api.request.SearchRequest;
import com.project.search.dto.PrepareResponse;
import com.project.search.errorhandling.exceptions.ApiFieldException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;

@Service
public class SearchService {

  @Autowired PrepareResponse prepareResponse;

  public String fetch(SearchRequest request) throws JsonProcessingException {
    if (request.getType() == null || request.getType().equals("1")) {
      switch (request.getEntity()) {
        case "ticket":
          return prepareResponse.getTicketList(request);
        case "user":
          return prepareResponse.getUserList(request);
        case "organization":
          return prepareResponse.getOrgList(request);
        default:
          throw new ApiFieldException(
                  MessageFormat.format("Please provide a valid entity", request.getType()));
      }
    } else if (request.getType().equals("2")) {
      //        As of now -> this supports ONLY tokenized search for tickets
      return prepareResponse.getFromToken(request);
    }

    return "";
  }
}
