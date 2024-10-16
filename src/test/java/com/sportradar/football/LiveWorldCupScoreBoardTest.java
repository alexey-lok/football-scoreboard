package com.sportradar.football;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.Assertions.assertThat;

public class LiveWorldCupScoreBoardTest {
    
    @Test
    void canStartMatch() {
        LiveWorldCupScoreBoard scoreboard = new LiveWorldCupScoreBoard();
        scoreboard.startMatch("Mexico", "Canada");
    }

    @Test
    void startMatchWithEmptyTeamNamesThrowsIllegalArgumentException() {
        LiveWorldCupScoreBoard scoreboard = new LiveWorldCupScoreBoard();
        String expectedMessage = "Team names cannot be null or empty";
        assertThatThrownBy(() -> {
            scoreboard.startMatch(null, null);
        })
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessage(expectedMessage);

        assertThatThrownBy(() -> {
            scoreboard.startMatch("", "");
        })
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessage(expectedMessage);
    }

    @Test
    void startMatchWithSameHomeAndAwayTeamThrowsIllegalArgumentException() {
        LiveWorldCupScoreBoard scoreboard = new LiveWorldCupScoreBoard();
        String expectedMessage = "Home and away teams cannot be the same";
        assertThatThrownBy(() -> {
            scoreboard.startMatch("team", "team");
        })
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessage(expectedMessage);
    }

    @Test
    void canNotStartTheSameMatchTwice() {
        LiveWorldCupScoreBoard scoreboard = new LiveWorldCupScoreBoard();
        
        scoreboard.startMatch("Mexico", "Canada");
        
        String expectedMessage = "Match Mexico-Canada already started";
        
        assertThatThrownBy(() -> {
            scoreboard.startMatch("Mexico", "Canada");
        })
        .isInstanceOf(IllegalStateException.class)
        .hasMessage(expectedMessage);
    }

    @Test
    void matchStartsWithZeroScore() {
        LiveWorldCupScoreBoard scoreboard = new LiveWorldCupScoreBoard();
        scoreboard.startMatch("Mexico", "Canada");

        assertThat(scoreboard.getMatchScore("Mexico", "Canada"))
            .isEqualTo(new MatchScore(0, 0));
    }
}