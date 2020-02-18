package com.project.search.index;

import com.project.search.errorhandling.exceptions.ApiFieldException;
import com.project.search.model.User;
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
public class UserIndex {
  //    Cache to hold the indexed data
  //  Target < field name -> < field value, User[]>
  HashMap<String, HashMap<String, Set<User>>> indexMap;

  //    this holds the fields of the class
  Field[] fieldsRef;

  //    Set of all the valid fields
  Set<String> fieldSet;

  // initialize
  public UserIndex() {

    indexMap = new HashMap<>();
    // get all the attributes of the class
    fieldsRef = User.class.getDeclaredFields();
    // Store all the fields in a set
    fieldSet = new HashSet<>();

    // create cache memory for the fields
    for (Field fieldRef : fieldsRef) {
      fieldRef.setAccessible(true); // make private variables accessible for read
      String field = fieldRef.getName();
      indexMap.put(field, new HashMap<String, Set<User>>());
      fieldSet.add(field);
    }
  }

  // create index for one user
  public void set(User user) throws NoSuchFieldException, IllegalAccessException {

    // Update cache for all fields
    for (Field fieldRef : fieldsRef) {
      String field = fieldRef.getName();
      Object obj = fieldRef.get(user);
      if (obj != null) {

        if (field == "tags") { //String array
          for (int i = 0; i < Array.getLength(obj); i++) {
            String fieldValue = (String) Array.get(obj, i);
            indexMap.get(field).putIfAbsent(fieldValue.toLowerCase(), new HashSet<>());
            indexMap.get(field).get(fieldValue.toLowerCase()).add(user);
          }
        } else {
          String fieldValue = obj.toString();
          //            prepare the cache
          indexMap.get(field).putIfAbsent(fieldValue.toLowerCase(), new HashSet<>());
          indexMap.get(field).get(fieldValue.toLowerCase()).add(user);
        }
      }
    }
  }

  // create index for multiple users
  public void set(User[] users) throws NoSuchFieldException, IllegalAccessException {
    for (User user : users) {
      set(user);
    }
  }

  public User[] get(String field, String value) {
    //        Check if the field is a valid field
    if (!fieldSet.contains(field)) {
      throw new ApiFieldException(MessageFormat.format("Field not valid : {0}",field));
    }

    Set<User> userSet = indexMap.get(field).get(value.toLowerCase());
    if (userSet != null) {
      User[] res = new User[userSet.size()];
      userSet.toArray(res);
      return res;
    }
    return null;
  }
}
