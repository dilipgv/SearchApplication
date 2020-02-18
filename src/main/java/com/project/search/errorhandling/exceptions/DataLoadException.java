package com.project.search.errorhandling.exceptions;

import lombok.Data;

@Data
// static exception
public class DataLoadException extends Exception {
  private String message;

  public DataLoadException(String message) {
    super(message);
    this.message = message;
  }
}
