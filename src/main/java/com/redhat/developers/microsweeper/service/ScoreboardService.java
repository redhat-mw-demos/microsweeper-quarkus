package com.redhat.developers.microsweeper.service;

import com.redhat.developers.microsweeper.model.Score;

import io.micrometer.core.annotation.Timed;
import io.micrometer.core.instrument.Metrics;
import io.micrometer.core.instrument.Timer;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;

import java.util.List;

@ApplicationScoped
public class ScoreboardService  {

    public List<Score> getScoreboard() {
        return Score.listAll();
    }

    @Transactional
    @Timed(description = "How log to add a score", value = "scoreboard-timer")
    public void addScore(Score score) {
        Timer timer = Metrics.globalRegistry.timer("example.prime.number.test");
        timer.record(() -> {
            Score.persist(score);
        });
    }

    @Transactional
    public Long clearScores() {
        return Score.deleteAll();
    }

}
