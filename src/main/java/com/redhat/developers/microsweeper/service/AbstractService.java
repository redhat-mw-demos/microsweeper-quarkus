package com.redhat.developers.microsweeper.service;

import java.util.HashMap;
import java.util.Map;

import com.redhat.developers.microsweeper.model.Score;

import software.amazon.awssdk.services.dynamodb.model.AttributeValue;
import software.amazon.awssdk.services.dynamodb.model.GetItemRequest;
import software.amazon.awssdk.services.dynamodb.model.PutItemRequest;
import software.amazon.awssdk.services.dynamodb.model.ScanRequest;

public class AbstractService {

    public String name;
    public String level;
    public String time;
    public String success;
    
    public static final String SCORE_NAME_COL = "name";
    public static final String SCORE_LEVEL_COL = "level";
    public static final String SCORE_TIME_COL = "time";
    public static final String SCORE_SUCCESS_COL = "success";

    public String getTableName() {
        return "score";
    }

    protected ScanRequest scanRequest() {
        return ScanRequest.builder().tableName(getTableName())
                .attributesToGet(SCORE_NAME_COL, SCORE_LEVEL_COL, SCORE_TIME_COL, SCORE_SUCCESS_COL).build();
    }

    protected PutItemRequest putRequest(Score score) {
        Map<String, AttributeValue> item = new HashMap<>();
        item.put(SCORE_NAME_COL, AttributeValue.builder().s(score.getName()).build());
        item.put(SCORE_LEVEL_COL, AttributeValue.builder().s(score.getLevel()).build());
        item.put(SCORE_TIME_COL, AttributeValue.builder().s(score.getTime()).build());
        item.put(SCORE_SUCCESS_COL, AttributeValue.builder().s(score.getSuccess()).build());

        return PutItemRequest.builder()
                .tableName(getTableName())
                .item(item)
                .build();
    }

    protected GetItemRequest getRequest(String name) {
        Map<String, AttributeValue> key = new HashMap<>();
        key.put(SCORE_NAME_COL, AttributeValue.builder().s(name).build());

        return GetItemRequest.builder()
                .tableName(getTableName())
                .key(key)
                .attributesToGet(SCORE_NAME_COL, SCORE_LEVEL_COL, SCORE_TIME_COL, SCORE_SUCCESS_COL)
                .build();
    }
    
}
