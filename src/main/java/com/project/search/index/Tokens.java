package com.project.search.index;

import com.project.search.api.response.FuzzySearch;
import com.project.search.model.Organization;
import com.project.search.model.Ticket;
import com.project.search.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class Tokens {
  // tokenize based on Subject and description
  //           < keyword , ticketlist>
  HashMap<String, Set<Ticket>> ticketToken;

  public Tokens() {
    ticketToken = new HashMap<>();
  }

  @Autowired Trie trie;
  public void tokenizeTicket(Ticket[] tickets) {
    for (Ticket ticket : tickets) {
      // create keywords from ticket
      String content = ticket.getSubject() +" " + ticket.getDescription();
      String[] tokens = content.toLowerCase().split(" ");

      //todo - tokenize only keywords, remove symbols etc
      for (String token : tokens) {
        ticketToken.putIfAbsent(token, new HashSet<>());
        ticketToken.get(token).add(ticket);

        //Push this tokens into Trie for lookup
          trie.insert(token);

      }
    }
  }

  public String[] getMatches(String token){
      return trie.search(token);
  }

  public FuzzySearch fetchBytoken(String token){

      String[] tokens = getMatches(token.toLowerCase());

      if (tokens == null)return null;

      List<Ticket> tickets = new ArrayList<>();

      for (String tkn : tokens){
          tickets.addAll(Arrays.asList(getTickets(tkn)));
      }

      //todo for other objects

//    Return Fuzzy search results
      FuzzySearch fuzzySearch = new FuzzySearch();
      fuzzySearch.setTokens(tokens);
      fuzzySearch.setTickets(tickets.toArray(new Ticket[tickets.size()]));
      return fuzzySearch;
  }

  public Ticket[] getTickets(String token) {
    if (token == null) return null;
    Set<Ticket> tickets = ticketToken.get(token.toLowerCase());
    if (tickets != null) {
      Ticket[] res = new Ticket[tickets.size()];
      tickets.toArray(res);
      return res;
    }
    return null;
  }
}
