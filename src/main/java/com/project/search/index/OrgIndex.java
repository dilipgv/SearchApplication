package com.project.search.index;

import com.project.search.errorhandling.exceptions.ApiFieldException;
import com.project.search.model.Organization;
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
public class OrgIndex {
  //    Cache to hold the indexed data
  //  Target < field name -> < field value, Organization[]>
  HashMap<String, HashMap<String, Set<Organization>>> indexMap;

  //    this holds the fields of the class
  Field[] fieldsRef;

  //    Set of all the valid fields
  Set<String> fieldSet;

  // initialize
  public OrgIndex() {

    indexMap = new HashMap<>();
    // get all the attributes of the class
    fieldsRef = Organization.class.getDeclaredFields();
    // Store all the fields in a set
    fieldSet = new HashSet<>();

    // create cache memory for the fields
    for (Field fieldRef : fieldsRef) {
      fieldRef.setAccessible(true); // make private variables accessible for read
      String field = fieldRef.getName();
      indexMap.put(field, new HashMap<String, Set<Organization>>());
      fieldSet.add(field);
    }
  }

  // create index for one organization
  public void set(Organization organization) throws NoSuchFieldException, IllegalAccessException {

    // Update cache for all fields
    for (Field fieldRef : fieldsRef) {
      String field = fieldRef.getName();
      Object obj = fieldRef.get(organization);

      if (obj != null) {

        if (field == "tags" || field == "domain_names") {//String array
          for (int i = 0; i < Array.getLength(obj); i++) {
            String fieldValue = (String) Array.get(obj, i);
            indexMap.get(field).putIfAbsent(fieldValue.toLowerCase(), new HashSet<>());
            indexMap.get(field).get(fieldValue.toLowerCase()).add(organization);
          }
        } else {
          String fieldValue = obj.toString();
          //            prepare the cache
          indexMap.get(field).putIfAbsent(fieldValue.toLowerCase(), new HashSet<>());
          indexMap.get(field).get(fieldValue.toLowerCase()).add(organization);
        }
      }
    }
  }

  // create index for multiple organizations
  public void set(Organization[] organizations)
      throws NoSuchFieldException, IllegalAccessException {
    for (Organization organization : organizations) {
      set(organization);
    }
  }

  public Organization[] get(String field, String value) {
    //        Check if the field is a valid field
    if (!fieldSet.contains(field)) {
      throw new ApiFieldException(MessageFormat.format("Field not valid : {0}",field));
    }

    Set<Organization> orgSet = indexMap.get(field).get(value.toLowerCase());
    if (orgSet != null) {
      Organization[] res = new Organization[orgSet.size()];
      orgSet.toArray(res);
      return res;
    }
    return null;
  }
}
