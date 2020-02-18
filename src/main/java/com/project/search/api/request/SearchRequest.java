package com.project.search.api.request;

import lombok.Data;

import java.util.Set;

@Data
public class SearchRequest {
    private String entity;
    private String type;
    private SearchQuery query;
    private String queryString;
    private Set<String> fetch;
}
