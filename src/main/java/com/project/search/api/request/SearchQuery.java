package com.project.search.api.request;

import lombok.Data;

@Data
public class SearchQuery {
    private String field;
    private char operator;
    private String value;
}
