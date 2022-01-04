package com.baremetalcode.db.domain;

import lombok.Data;

import java.util.UUID;

@Data
public class User {
    private String uuid = UUID.randomUUID().toString();
    private String firstName;
    private String lastName;
    private String countryISO;
    private UserAddress userAddress;
}
