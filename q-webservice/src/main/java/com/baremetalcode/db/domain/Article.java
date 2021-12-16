package com.baremetalcode.db.domain;

import lombok.Data;

import java.util.UUID;

@Data
public class Article {
    private String uuid = UUID.randomUUID().toString();
    private String userId;
    private String title;
    private String header;
    private String body;
}