package com.project.search.model;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class Organization {
    private int _id;
    private String url;
    private String external_id;
    private String name;
    private String[] domain_names;
    private String created_at;
    private String details;
    private boolean shared_tickets;
    private String[] tags;

}
