package com.project.search.api.response;

import com.project.search.model.Organization;
import com.project.search.model.Ticket;
import com.project.search.model.User;
import lombok.Data;

@Data
public class UserList extends User {
    private Organization organization;
    private Ticket[] tickets;

}
