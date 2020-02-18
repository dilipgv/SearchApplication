package com.project.search.validation.api;

import com.project.search.api.request.SearchQuery;
import com.project.search.api.request.SearchRequest;
import com.project.search.errorhandling.exceptions.ApiFieldException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;

@Service
public class SearchRequestValidator {

  private static Logger logger = LoggerFactory.getLogger(SearchRequestValidator.class);

  public boolean validate(SearchRequest request) throws ApiFieldException {

    logger.debug("Validating Request");

    if (request.getType() == null
        || request.getType().equals("1")
        || request.getType().equals("2")) {
    } else {
      throw new ApiFieldException(
          MessageFormat.format("The request type {0} is not valid", request.getType()));
    }

    if (request.getType() == null) return true;

    if (request.getType().equals("1")) {
      if (request.getQuery() == null) {
        throw new ApiFieldException(
            MessageFormat.format("Please specify the entity to be filtered", request.getType()));
      }

      if (!isValidQuery(request.getQuery())) {
        throw new ApiFieldException(
            MessageFormat.format(
                "Query needs to have field, operator(=) and value", request.getType()));
      }
    }

    if (request.getType().equals("2")) {
      if (request.getQueryString() == null) {
        throw new ApiFieldException(
            MessageFormat.format("Please provide the parameter : querystring", request.getType()));
      }
    }

    logger.debug("Validating successful");

    // Valid request
    return true;
  }

  private boolean isValidQuery(SearchQuery query) {
    //    Current implementation only supports "="
    if (query.getField() == null || query.getOperator() != '=' || query.getValue() == null) {
      return false;
    }

    return true;
  }
}
