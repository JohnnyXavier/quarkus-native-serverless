package com.baremetalcode.db.dynamo.repos;

import com.baremetalcode.db.domain.User;
import com.baremetalcode.db.dynamo.DynamoOps;
import com.baremetalcode.db.mappers.DomainMapper;
import io.quarkus.cache.CacheResult;
import io.smallrye.mutiny.Uni;
import software.amazon.awssdk.services.dynamodb.DynamoDbAsyncClient;
import software.amazon.awssdk.services.dynamodb.model.PutItemRequest;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.util.List;
import java.util.stream.Collectors;

@ApplicationScoped
public class UsersRepo extends DynamoOps {

    public static final String TABLE_NAME = "Users";
    public static final String COL_UUID = "uuid";
    public static final String COL_FIRST_NAME = "firstName";
    public static final String COL_LAST_NAME = "lastName";
    public static final String COL_COUNTRY_ISO = "countryISO";
    public static final String ATTR_USER_ADDRESS = "userAddress";

    @Inject
    DynamoDbAsyncClient dynamoDbAsync;

    private PutItemRequest putUser(final User user) {
        return PutItemRequest.builder()
                .tableName(TABLE_NAME)
                .item(DomainMapper.fromUser(user))
                .build();
    }

    public Uni<List<User>> putAsync(final User user) {
        return Uni.createFrom()
                .completionStage(() -> dynamoDbAsync.putItem(putUser(user)))
                .onItem()
                .ignore()
                .andSwitchTo(this::findAll);
    }

    @CacheResult(cacheName = "users-all")
    public Uni<List<User>> findAll() {
        return Uni.createFrom()
                .completionStage(() -> dynamoDbAsync.scan(scanRequest(TABLE_NAME)))
                .onItem()
                .transform(scanResponse -> scanResponse.items()
                        .stream()
                        .map(DomainMapper::toUser)
                        .collect(Collectors.toList()));
    }

    @CacheResult(cacheName = "users-by-id")
    public Uni<User> findById(final String userUuid) {
        return Uni.createFrom()
                .completionStage(() -> dynamoDbAsync.getItem(findById(userUuid, COL_UUID, TABLE_NAME)))
                .onItem()
                .transform(response -> DomainMapper.toUser(response.item()));
    }

    @CacheResult(cacheName = "users-by-countryId")
    public Uni<List<User>> findUserByCountryIsoId(final String countryIso) {
        return Uni.createFrom()
                .completionStage(() -> dynamoDbAsync.scan(scanSingleAttribute(countryIso, ":countryISO", "countryISO = :countryISO", TABLE_NAME)))
                .onItem()
                .transform(scanResponse -> scanResponse.items()
                        .stream()
                        .map(DomainMapper::toUser)
                        .collect(Collectors.toList()));
    }
}
