package com.project.search.index;

import com.project.search.errorhandling.exceptions.ApiFieldException;
import com.project.search.model.Ticket;
import lombok.Data;
import org.springframework.stereotype.Component;

import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.text.MessageFormat;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

@Component
@Data
public class TicketIndex {
  //    Cache to hold the indexed data
  //  Target < field name -> < field value, Ticket[]>
  HashMap<String, HashMap<String, Set<Ticket>>> indexMap;

  //    this holds the fields of the class
  Field[] fieldsRef;

  //    Set of all the valid fields
  Set<String> fieldSet;

  // initialize
  public TicketIndex() {

    indexMap = new HashMap<>();
    // get all the attributes of the class
    fieldsRef = Ticket.class.getDeclaredFields();
    // Store all the fields in a set
    fieldSet = new HashSet<>();

    // create cache memory for the fields
    for (Field fieldRef : fieldsRef) {
      fieldRef.setAccessible(true); // make private variables accessible for read
      String field = fieldRef.getName();
      indexMap.put(field, new HashMap<String, Set<Ticket>>());
      fieldSet.add(field);
    }
  }

  // create index for one ticket
  public void set(Ticket ticket) throws NoSuchFieldException, IllegalAccessException {

    // Update cache for all fields
    for (Field fieldRef : fieldsRef) {
      String field = fieldRef.getName();
      Object obj = fieldRef.get(ticket);
      if (obj != null) {

        if (field == "tags") {//String array
          for (int i = 0; i < Array.getLength(obj); i++) {
            String fieldValue = (String) Array.get(obj, i);
            indexMap.get(field).putIfAbsent(fieldValue.toLowerCase(), new HashSet<>());
            indexMap.get(field).get(fieldValue.toLowerCase()).add(ticket);
          }
        } else {

          String fieldValue = obj.toString();
          //            prepare the cache
          indexMap.get(field).putIfAbsent(fieldValue.toLowerCase(), new HashSet<>());
          indexMap.get(field).get(fieldValue.toLowerCase()).add(ticket);
        }
      }
    }
  }

  // create index for multiple tickets
  public void set(Ticket[] tickets) throws NoSuchFieldException, IllegalAccessException {
    for (Ticket ticket : tickets) {
      set(ticket);
    }
  }

  public Ticket[] get(String field, String value) {
    //        Check if the field is a valid field
    if (!fieldSet.contains(field)) {
      throw new ApiFieldException(MessageFormat.format("Field not valid : {0}",field));
    }

    Set<Ticket> ticketSet = indexMap.get(field).get(value.toLowerCase());
    if (ticketSet != null) {
      Ticket[] res = new Ticket[ticketSet.size()];
      ticketSet.toArray(res);
      return res;
    }
    return null;
  }
}
