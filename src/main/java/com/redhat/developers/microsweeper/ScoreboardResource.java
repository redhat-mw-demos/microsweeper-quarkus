package com.redhat.developers.microsweeper;

import com.redhat.developers.microsweeper.model.Score;
import com.redhat.developers.microsweeper.service.ScoreboardService;

import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.MediaType;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import software.amazon.awssdk.services.ses.SesClient;

import java.util.List;

@Path("/api/scoreboard")
public class ScoreboardResource {

    final Logger logger = LoggerFactory.getLogger(getClass());

    // Replace with your verified email addresses for sender and recipient 
    public static final String EMAIL_FROM_ADDRESS = "SENDER_EMAIL_ADDRESS";
    public static final String EMAIL_TO_ADDRESS = "RECIPIENT_EMAIL_ADDRESS";

    @Inject
    ScoreboardService scoreboardService;

    @Inject
    SesClient ses;

    @GET
    public List<Score> getScoreboard() {
        return scoreboardService.getScoreboard();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public void addScore(Score score) throws Exception {
        scoreboardService.addScore(score);
        // encrypt(score);
    }

    public void encrypt(Score score) {
        logger.info("New receiver: " + score.getName());
        ses.sendEmail(req -> req
            .source(EMAIL_FROM_ADDRESS)
            .destination(d -> d.toAddresses(EMAIL_TO_ADDRESS))
            .message(msg -> msg
                .subject(sub -> sub.data("[" + score.getName() + "] You've got a new score in Microsweeper!"))
                .body(b -> b.text(txt -> txt.data("Congrats!!! " + score.getName() + " is just completed the Microsweeper with " + score.getLevel()))))).messageId();
    }

}
