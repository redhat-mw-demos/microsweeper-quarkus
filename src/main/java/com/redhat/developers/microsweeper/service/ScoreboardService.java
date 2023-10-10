package com.redhat.developers.microsweeper.service;

import com.redhat.developers.microsweeper.model.Score;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import java.util.List;
import java.util.stream.Collectors;

import software.amazon.awssdk.services.dynamodb.DynamoDbClient;

@ApplicationScoped
public class ScoreboardService extends AbstractService {

    @Inject
    DynamoDbClient dynamoDB;

    public List<Score> getScoreboard() {
        return dynamoDB.scanPaginator(scanRequest()).items().stream()
                .map(Score::from)
                .collect(Collectors.toList());
    }

    public void addScore(Score score) {
        dynamoDB.putItem(putRequest(score));
    }

}
