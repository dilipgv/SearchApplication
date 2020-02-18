package com.project.search.model;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class Ticket {
    private String _id;
    private String url;
    private String external_id;
    private String created_at;
    private String type;
    private String subject;
    private String description;
    private String priority;
    private String status;
    private int submitter_id;
    private int assignee_id;
    private int organization_id;
    private String[] tags;
    private boolean has_incidents;
    private String due_at;
    private String via;
}
