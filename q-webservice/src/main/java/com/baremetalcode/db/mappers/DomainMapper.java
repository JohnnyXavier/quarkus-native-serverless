package com.baremetalcode.db.mappers;

import com.baremetalcode.db.domain.Article;
import com.baremetalcode.db.domain.User;
import com.baremetalcode.db.dynamo.repos.ArticlesRepo;
import com.baremetalcode.db.dynamo.repos.UsersRepo;
import software.amazon.awssdk.services.dynamodb.model.AttributeValue;

import java.util.HashMap;
import java.util.Map;

public class DomainMapper {

    public static Article toArticle(final Map<String, AttributeValue> item) {
        Article article = new Article();

        if (item != null && !item.isEmpty()) {
            article.setUuid(item.get(ArticlesRepo.COL_UUID).s());
            article.setUserId(item.get(ArticlesRepo.COL_USER_ID).s());
            article.setTitle(item.get(ArticlesRepo.COL_TITLE).s());
            article.setHeader(item.get(ArticlesRepo.COL_HEADER).s());
            article.setBody(item.get(ArticlesRepo.COL_BODY).s());
        }

        return article;
    }

    public static User toUser(final Map<String, AttributeValue> userMap) {
        User user = new User();

        if (userMap != null && !userMap.isEmpty()) {
            user.setUuid(userMap.get(UsersRepo.COL_UUID).s());
            user.setFirstName(userMap.get(UsersRepo.COL_FIRST_NAME).s());
            user.setLastName(userMap.get(UsersRepo.COL_LAST_NAME).s());
            user.setCountryISO(userMap.get(UsersRepo.COL_COUNTRY_ISO).s());
        }

        return user;
    }


    public static Map<String, AttributeValue> fromUser(final User user) {
        Map<String, AttributeValue> userMap = new HashMap<>();

        userMap.put(UsersRepo.COL_UUID, AttributeValue.builder().s(user.getUuid()).build());
        userMap.put(UsersRepo.COL_FIRST_NAME, AttributeValue.builder().s(user.getFirstName()).build());
        userMap.put(UsersRepo.COL_LAST_NAME, AttributeValue.builder().s(user.getLastName()).build());
        userMap.put(UsersRepo.COL_COUNTRY_ISO, AttributeValue.builder().s(user.getCountryISO()).build());

        return userMap;
    }

    public static Map<String, AttributeValue> fromArticle(final Article article) {
        Map<String, AttributeValue> articleMap = new HashMap<>();

        articleMap.put(ArticlesRepo.COL_UUID, AttributeValue.builder().s(article.getUuid()).build());
        articleMap.put(ArticlesRepo.COL_USER_ID, AttributeValue.builder().s(article.getUserId()).build());
        articleMap.put(ArticlesRepo.COL_TITLE, AttributeValue.builder().s(article.getTitle()).build());
        articleMap.put(ArticlesRepo.COL_BODY, AttributeValue.builder().s(article.getBody()).build());
        articleMap.put(ArticlesRepo.COL_HEADER, AttributeValue.builder().s(article.getHeader()).build());

        return articleMap;
    }
}
