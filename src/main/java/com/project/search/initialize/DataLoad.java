package com.project.search.initialize;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.search.adapter.data.FileDataLoad;
import com.project.search.errorhandling.exceptions.DataLoadException;
import com.project.search.index.OrgIndex;
import com.project.search.index.TicketIndex;
import com.project.search.index.Tokens;
import com.project.search.index.UserIndex;
import com.project.search.model.Organization;
import com.project.search.model.Ticket;
import com.project.search.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.IOException;

@Component
public class DataLoad {
  // data load from Files
  @Autowired FileDataLoad fileDataLoad;
  @Autowired TicketIndex ticketIndex;
  @Autowired OrgIndex orgIndex;
  @Autowired UserIndex userIndex;
  @Autowired Tokens tokens;
  private static Logger logger = LoggerFactory.getLogger(DataLoad.class);

  @PostConstruct // Bootstrap the initializer
  public void init() throws DataLoadException {
    logger.info("Initializing the cache..");
    // load
    try {
      String users = fileDataLoad.load("user");
      String tickets = fileDataLoad.load("ticket");
      String organizations = fileDataLoad.load("organization");
      logger.debug("Data Load Complete");
      // System.out.println("Loaded");
      if (users == null || organizations == null || tickets == null) {
        throw new DataLoadException("Data Load from resource failed");
      }
      // Map
      Ticket[] ticketList = new ObjectMapper().readValue(tickets, Ticket[].class);
      Organization[] orgList = new ObjectMapper().readValue(organizations, Organization[].class);
      User[] userList = new ObjectMapper().readValue(users, User[].class);

      logger.debug("Mapping successful to the model");

      logger.debug("Indexing..");
      // Index -- cache
      ticketIndex.set(ticketList);
      orgIndex.set(orgList);
      userIndex.set(userList);

      logger.debug("Indexing successful");

      // tokenize
      tokens.tokenizeTicket(ticketList);
      logger.debug("Tickets data tokenized");
      logger.info("Cache initialized successfully");

    } catch (IOException | NoSuchFieldException | IllegalAccessException e) {
      throw new DataLoadException("Data Load from resource failed");
    }
  }
}
