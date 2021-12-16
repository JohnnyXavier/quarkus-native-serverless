package com.baremetalcode.db.dynamo;

import software.amazon.awssdk.services.dynamodb.model.AttributeValue;
import software.amazon.awssdk.services.dynamodb.model.GetItemRequest;
import software.amazon.awssdk.services.dynamodb.model.ScanRequest;

import java.util.HashMap;
import java.util.Map;

public abstract class DynamoOps {
    protected ScanRequest scanRequest(final String tableName) {
        return ScanRequest.builder()
                .tableName(tableName)
                .build();
    }

    protected GetItemRequest findById(final String uuid, final String uuidColumn, final String tableName) {
        Map<String, AttributeValue> getItemKey = new HashMap<>();
        getItemKey.put(uuidColumn, AttributeValue.builder().s(uuid).build());

        return GetItemRequest.builder()
                .tableName(tableName)
                .key(getItemKey)
                .build();
    }

    protected ScanRequest scanSingleAttribute(final String attrValue, final String attrName, final String filterExpression, final String tableName) {
        Map<String, AttributeValue> valueMap = new HashMap<>();
        valueMap.put(attrName, AttributeValue.builder().s(attrValue).build());

        return ScanRequest.builder()
                .tableName(tableName)
                .expressionAttributeValues(valueMap)
                .filterExpression(filterExpression)
                .build();
    }
}
