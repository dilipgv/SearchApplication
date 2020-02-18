package com.project.search.api.response;

import com.project.search.model.Organization;
import com.project.search.model.Ticket;
import com.project.search.model.User;
import lombok.Data;
import org.springframework.beans.BeanUtils;

import java.util.List;

@Data
public class OrgList extends Organization {
    private User[] users;
    private Ticket[] tickets;
}

