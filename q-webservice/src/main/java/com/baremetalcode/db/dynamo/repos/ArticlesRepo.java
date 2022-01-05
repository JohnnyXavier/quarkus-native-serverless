package com.baremetalcode.db.dynamo.repos;

import com.baremetalcode.db.domain.Article;
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
public class ArticlesRepo extends DynamoOps {

    public static final String TABLE_NAME = "Articles";
    public static final String COL_UUID = "uuid";
    public static final String COL_USER_ID = "userId";
    public static final String COL_TITLE = "title";
    public static final String COL_HEADER = "header";
    public static final String COL_BODY = "body";

    @Inject
    DynamoDbAsyncClient dynamoDbAsync;

    protected PutItemRequest putArticle(final Article article) {
        return PutItemRequest.builder()
                .tableName(TABLE_NAME)
                .item(DomainMapper.fromArticle(article))
                .build();
    }

    public Uni<List<Article>> putAsync(final Article article) {
        return Uni.createFrom()
                .completionStage(() -> dynamoDbAsync.putItem(putArticle(article)))
                .onItem()
                .ignore()
                .andSwitchTo(this::findAll);
    }

    @CacheResult(cacheName = "articles-all")
    public Uni<List<Article>> findAll() {
        return Uni.createFrom()
                .completionStage(() -> dynamoDbAsync.scan(scanRequest(TABLE_NAME)))
                .onItem()
                .transform(scanResponse -> scanResponse.items()
                        .stream()
                        .map(DomainMapper::toArticle)
                        .collect(Collectors.toList()));
    }

    @CacheResult(cacheName = "articles-by-id")
    public Uni<Article> findById(final String articleUuid) {
        return Uni.createFrom()
                .completionStage(() -> dynamoDbAsync.getItem(findById(articleUuid, COL_UUID, TABLE_NAME)))
                .onItem()
                .transform(response -> DomainMapper.toArticle(response.item()));
    }

    @CacheResult(cacheName = "articles-by-user-id")
    public Uni<List<Article>> findArticleByUserId(final String userUuid) {
        return Uni.createFrom()
                .completionStage(() -> dynamoDbAsync.scan(scanSingleAttribute(userUuid, ":userUuid", "userId = :userUuid", TABLE_NAME)))
                .onItem()
                .transform(scanResponse -> scanResponse.items()
                        .stream()
                        .map(DomainMapper::toArticle)
                        .collect(Collectors.toList()));
    }
}
