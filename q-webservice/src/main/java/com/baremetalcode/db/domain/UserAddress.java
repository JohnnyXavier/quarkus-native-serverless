package com.baremetalcode.db.domain;

import lombok.Data;

@Data
public class UserAddress {
    private String streetName;
    private String streetNumber;
    private String city;
    private String zipCode;
}
