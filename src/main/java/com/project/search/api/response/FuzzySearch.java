package com.project.search.api.response;

import com.project.search.model.Ticket;
import lombok.Data;

@Data
public class FuzzySearch {
    private String[] tokens;
    private Ticket[] tickets;
}
