package com.project.search.model;


import lombok.Data;

import java.time.LocalDateTime;

@Data
public class User {
    private int _id;
    private String url;
    private String external_id;
    private String name;
    private String alias;
    private String created_at;
    private boolean active;
    private boolean verified;
    private boolean shared;
    private String locale;
    private String timezone;
    private String last_login_at;
    private String email;
    private String phone;
    private String signature;
    private int organization_id;
    private String[] tags;
    private boolean suspended;
    private String role;
}
