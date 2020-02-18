package com.project.search.errorhandling.exceptions;

// all run time exceptions
public class ApiFieldException extends RuntimeException {
  public ApiFieldException(String message) {
    super(message);
  }
}
