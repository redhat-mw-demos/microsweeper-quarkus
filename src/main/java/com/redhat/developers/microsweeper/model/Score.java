package com.redhat.developers.microsweeper.model;

import java.util.Map;
import java.util.Objects;

import com.redhat.developers.microsweeper.service.AbstractService;

import io.quarkus.runtime.annotations.RegisterForReflection;
import software.amazon.awssdk.services.dynamodb.model.AttributeValue;

@RegisterForReflection
public class Score {

    public String name;
    public String level;
    public String time;
    public String success;

    public Score() { }

    public static Score from(Map<String, AttributeValue> item) {
        Score score = new Score();
        if (item != null && !item.isEmpty()) {
            score.setName(item.get(AbstractService.SCORE_NAME_COL).s());
            score.setLevel(item.get(AbstractService.SCORE_LEVEL_COL).s());
            score.setTime(item.get(AbstractService.SCORE_TIME_COL).s());
            score.setSuccess(item.get(AbstractService.SCORE_SUCCESS_COL).s());    

        }
        return score;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLevel() {
        return this.level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getTime() {
        return this.time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getSuccess() {
        return this.success;
    }

    public void setSuccess(String success) {
        this.success = success;
    }   

    @Override
    public int hashCode() {
        return Objects.hash(this.name);
    }

}
