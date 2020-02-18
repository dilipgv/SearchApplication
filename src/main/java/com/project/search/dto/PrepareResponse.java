package com.project.search.dto;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.search.api.request.SearchRequest;
import com.project.search.api.response.FuzzySearch;
import com.project.search.api.response.OrgList;
import com.project.search.api.response.TicketList;
import com.project.search.api.response.UserList;
import com.project.search.common.IConstants;
import com.project.search.index.OrgIndex;
import com.project.search.index.TicketIndex;
import com.project.search.index.Tokens;
import com.project.search.index.UserIndex;
import com.project.search.model.Organization;
import com.project.search.model.Ticket;
import com.project.search.model.User;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Component
public class PrepareResponse {
  @Autowired TicketIndex ticketIndex;
  @Autowired OrgIndex orgIndex;
  @Autowired UserIndex userIndex;
  @Autowired Tokens tokens;

  // ------------------------------------------------------------------------------------------
  //  -----------------------------PUBLIC METHODS----------------------------------------------
  // ------------------------------------------------------------------------------------------

  public String getTicketList(SearchRequest request) throws JsonProcessingException {
    // Get the tickets from the Index cache
    Ticket[] tickets =
        ticketIndex.get(request.getQuery().getField(), request.getQuery().getValue());
    if (tickets == null) return null;
    //    Map the data to the Response and add fetch options
    List<TicketList> res = mapTicketToResponse(tickets, request.getFetch());
    return new ObjectMapper().writeValueAsString(res);
  }

  public String getOrgList(SearchRequest request) throws JsonProcessingException {
    Organization[] orgs =
        orgIndex.get(request.getQuery().getField(), request.getQuery().getValue());
    if (orgs == null) return null;
    //    Map the data to the Response and add fetch options
    List<OrgList> res = mapOrgToResponse(orgs, request.getFetch());
    return new ObjectMapper().writeValueAsString(res);
  }

  public String getUserList(SearchRequest request) throws JsonProcessingException {
    User[] users = userIndex.get(request.getQuery().getField(), request.getQuery().getValue());
    if (users == null) return null;
    //    Map the data to the Response and add fetch options
    List<UserList> res = mapUserToResponse(users, request.getFetch());
    return new ObjectMapper().writeValueAsString(res);
  }

  //  Fetch the tickets based on token
  public String getFromToken(SearchRequest request) throws JsonProcessingException {
    //    read and fetch from TRIE
    FuzzySearch fuzzySearch = tokens.fetchBytoken(request.getQueryString().toLowerCase());
    if (fuzzySearch == null) return null;

    return new ObjectMapper().writeValueAsString(fuzzySearch);
  }

  // ------------------------------------------------------------------------------------------
  //  -----------------------------PRIVATE METHODS---------------------------------------------
  // ------------------------------------------------------------------------------------------
  private List<TicketList> mapTicketToResponse(Ticket[] tickets, Set<String> fetch) {
    List<TicketList> res = new ArrayList<>();
    // prepare the output
    for (Ticket ticket : tickets) {
      TicketList out = new TicketList();
      BeanUtils.copyProperties(ticket, out);
      if (fetch != null && fetch.contains(IConstants.ORGANIZATION)) {
        // set Organization
        String org_id = Integer.toString(ticket.getOrganization_id());
        Organization[] orgs = orgIndex.get(IConstants.ID, org_id);
        if (orgs != null) {
          out.setOrganization(orgs[0]);
        }
      }
      // set user
      if (fetch != null && fetch.contains(IConstants.USER)) {
        String assignee_id = Integer.toString(ticket.getAssignee_id());
        User[] users = userIndex.get(IConstants.ID, assignee_id);
        if (users != null) {
          out.setUser(users[0]);
        }
      }
      res.add(out);
    }
    return res;
  }

  private List<OrgList> mapOrgToResponse(Organization[] orgs, Set<String> fetch) {
    List<OrgList> res = new ArrayList<>();
    for (Organization org : orgs) {
      OrgList out = new OrgList();
      //      copy the properties from super class
      BeanUtils.copyProperties(org, out);
      String org_id = Integer.toString(org.get_id());
      //     set all the users of the Org
      if (fetch != null && fetch.contains(IConstants.USER)) {
        User[] users = userIndex.get(IConstants.ORGANIZATION_ID, org_id);
        if (users != null) {
          out.setUsers(users);
        }
      }
      //      Set all tickets of the org
      if (fetch != null && fetch.contains(IConstants.TICKET)) {
        Ticket[] tickets = ticketIndex.get(IConstants.ORGANIZATION_ID, org_id);
        if (tickets != null) {
          out.setTickets(tickets);
        }
      }
      res.add(out);
    }
    return res;
  }

  private List<UserList> mapUserToResponse(User[] users, Set<String> fetch) {
    List<UserList> res = new ArrayList<>();
    for (User user : users) {
      UserList out = new UserList();
      // copy the properties from super class
      BeanUtils.copyProperties(user, out);

      String user_id = Integer.toString(user.get_id());
      String org_id = Integer.toString(user.getOrganization_id());

      // set Org of the user
      if (fetch != null && fetch.contains(IConstants.ORGANIZATION)) {
        Organization[] orgs = orgIndex.get(IConstants.ID,org_id );
        if (orgs != null) {
          out.setOrganization(orgs[0]);
        }
      }

      //    set Tickets of the user
      if (fetch != null && fetch.contains(IConstants.TICKET)) {
        Ticket[] tickets = ticketIndex.get(IConstants.ASSIGNEE_ID, user_id);
        out.setTickets(tickets);
      }
      res.add(out);
    }
    return res;
  }
}
