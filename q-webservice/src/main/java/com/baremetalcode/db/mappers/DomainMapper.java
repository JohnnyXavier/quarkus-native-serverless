package com.baremetalcode.db.mappers;

import com.baremetalcode.db.domain.Article;
import com.baremetalcode.db.domain.User;
import com.baremetalcode.db.domain.UserAddress;
import com.baremetalcode.db.dynamo.repos.ArticlesRepo;
import com.baremetalcode.db.dynamo.repos.UsersRepo;
import software.amazon.awssdk.services.dynamodb.model.AttributeValue;

import java.util.HashMap;
import java.util.Map;

public class DomainMapper {

    public static Article toArticle(final Map<String, AttributeValue> articleMap) {
        if (articleMap == null || articleMap.isEmpty()) {
            return null;
        }

        final Article article = new Article();

        article.setUuid(articleMap.get(ArticlesRepo.COL_UUID).s());
        article.setUserId(articleMap.get(ArticlesRepo.COL_USER_ID).s());
        article.setTitle(articleMap.get(ArticlesRepo.COL_TITLE).s());
        article.setHeader(articleMap.get(ArticlesRepo.COL_HEADER).s());
        article.setBody(articleMap.get(ArticlesRepo.COL_BODY).s());

        return article;
    }

    public static User toUser(final Map<String, AttributeValue> userMap) {
        if (userMap == null || userMap.isEmpty()) {
            return null;
        }

        final User user = new User();

        if (userMap.get(UsersRepo.ATTR_USER_ADDRESS) != null) {
            final Map<String, AttributeValue> addressMap = userMap.get("userAddress").m();
            final UserAddress userAddress = new UserAddress();

            userAddress.setStreetName(addressMap.get("streetName").s());
            userAddress.setStreetNumber(addressMap.get("streetNumber").s());
            userAddress.setCity(addressMap.get("city").s());
            userAddress.setZipCode(addressMap.get("zipCode").s());

            user.setUserAddress(userAddress);
        }

        user.setUuid(userMap.get(UsersRepo.COL_UUID).s());
        user.setFirstName(userMap.get(UsersRepo.COL_FIRST_NAME).s());
        user.setLastName(userMap.get(UsersRepo.COL_LAST_NAME).s());
        user.setCountryISO(userMap.get(UsersRepo.COL_COUNTRY_ISO).s());

        return user;
    }


    public static Map<String, AttributeValue> fromUser(final User user) {
        final Map<String, AttributeValue> userMap = new HashMap<>();
        final Map<String, AttributeValue> addressMap = new HashMap<>();

        final UserAddress userAddress = user.getUserAddress();
        addressMap.put("streetName", AttributeValue.builder().s(userAddress.getStreetName()).build());
        addressMap.put("streetNumber", AttributeValue.builder().s(userAddress.getStreetNumber()).build());
        addressMap.put("city", AttributeValue.builder().s(userAddress.getCity()).build());
        addressMap.put("zipCode", AttributeValue.builder().s(userAddress.getZipCode()).build());

        userMap.put(UsersRepo.COL_UUID, AttributeValue.builder().s(user.getUuid()).build());
        userMap.put(UsersRepo.COL_FIRST_NAME, AttributeValue.builder().s(user.getFirstName()).build());
        userMap.put(UsersRepo.COL_LAST_NAME, AttributeValue.builder().s(user.getLastName()).build());
        userMap.put(UsersRepo.COL_COUNTRY_ISO, AttributeValue.builder().s(user.getCountryISO()).build());
        userMap.put(UsersRepo.ATTR_USER_ADDRESS, AttributeValue.builder().m(addressMap).build());

        return userMap;
    }

    public static Map<String, AttributeValue> fromArticle(final Article article) {
        final Map<String, AttributeValue> articleMap = new HashMap<>();

        articleMap.put(ArticlesRepo.COL_UUID, AttributeValue.builder().s(article.getUuid()).build());
        articleMap.put(ArticlesRepo.COL_USER_ID, AttributeValue.builder().s(article.getUserId()).build());
        articleMap.put(ArticlesRepo.COL_TITLE, AttributeValue.builder().s(article.getTitle()).build());
        articleMap.put(ArticlesRepo.COL_BODY, AttributeValue.builder().s(article.getBody()).build());
        articleMap.put(ArticlesRepo.COL_HEADER, AttributeValue.builder().s(article.getHeader()).build());

        return articleMap;
    }
}
